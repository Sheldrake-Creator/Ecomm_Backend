package com.service;

import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.dto.CartDTO;
import com.dto.CartItemDTO;
import com.exception.CartException;
import com.exception.RepositoryException;
import com.exception.UserException;
import com.mapper.CartMapper;
import com.model.Cart;
import com.repository.CartItemRepository;
import com.repository.CartRepository;
import com.util.LogUtils;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Override
    public CartDTO createCart(Long userId) {
        try {

            CartDTO cartDTO = new CartDTO();

            cartDTO.setUserId(userId);
            cartDTO.setTotalDiscountedPrice(0);
            cartDTO.setTotalItems(0);
            cartDTO.setTotalPrice(0);
            Cart cartEntity = this.cartRepository.save(cartMapper.toCart(cartDTO));

            return cartMapper.toCartDTO(cartEntity);
        } catch (IllegalArgumentException e) {
            logger.error("Cart Repository Error: {}", e);
            throw new IllegalArgumentException(
                    "An unexpected error occurred while retrieving the cart for user with ID: ", e);
        }
    }

    public CartDTO findCartByCartId(Long cartId) throws CartException {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (!optionalCart.isPresent()) {
            throw new CartException("Cart with ID: " + cartId + " not found");
        }
        CartDTO cart = cartMapper.toCartDTO(optionalCart.get());
        return cart;
    }

    @Override
    @Transactional(readOnly = true)
    public CartDTO findUserCart(Long userId) throws CartException {
        try {

            logger.debug("Enter: findUserCart");
            logger.debug("Finding cart for user with ID: {}", userId);
            Optional<Cart> optionalCart = cartRepository.findByUserId(userId);

            // Handle case when cart is not found
            if (!optionalCart.isPresent()) {
                throw new UserException("Cart not found with UserId");
            }

            CartDTO cart = cartMapper.toCartDTO(optionalCart.get());
            logger.debug("Is this what's fucking me? {}", cart);
            LogUtils.exit();
            return cart;

        } catch (DataAccessException | UserException e) {
            logger.error("Data access error while finding cart for user with ID: {}", userId, e);
            throw new CartException("An unexpected error occurred while retrieving the cart for user with ID: ", e);
        }
    }

    @Override
    @Transactional
    public CartDTO getUserCart(Long userId) throws CartException {
        try {
            CartDTO cart;
            logger.debug("Fetching cart for user: {}", userId);
            Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
            if (!optionalCart.isPresent()) {
                logger.debug("User has no cart. Creating Cart instead");
                cart = this.createCart(userId);
            } else {
                cart = cartMapper.toCartDTO(optionalCart.get());
            }
            return cart;
        } catch (DataAccessException e) {
            logger.error("Unexpected error occurred while getting user cart: ", e);
            throw new CartException("Unexpected error occurred while getting user cart", e);
        }
    }

    @Override
    public CartDTO syncCartWithCartItems(CartDTO cart) throws CartException {
        try {

            Set<CartItemDTO> cartItems = cart.getCartItems();
            Integer newTotalPrice = 0;
            Integer newTotalItems = 0;
            Integer newTotalDiscountedPrice = 0;

            for (CartItemDTO cartItem : cartItems) {
                newTotalItems += cartItem.getQuantity();
                newTotalPrice += (cartItem.getPrice() * cartItem.getQuantity());
                newTotalDiscountedPrice += (cartItem.getDiscountedPrice() * cartItem.getQuantity());
            }

            cart.setTotalDiscountedPrice(newTotalDiscountedPrice);
            cart.setTotalItems(newTotalItems);
            cart.setTotalPrice(newTotalPrice);
            cart.setCartItems(cartItems);

            this.cartRepository.save(cartMapper.toCart(cart));

            return cart;

        } catch (DataAccessException e) {
            e.getStackTrace();
            throw new CartException("Cart not found");
        }
    }

    @Override
    public void checkoutCart(Long cartId) throws CartException {
        try {
            // Delete User Cart
            this.cartItemRepository.deleteCartItemsByCartId(cartId);
            // Delete CartItems
            this.cartRepository.deleteCartById(cartId);

        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

}
