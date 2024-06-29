package com.service;

import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.dto.CartDTO;
import com.dto.CartItemDTO;
import com.dto.UserDTO;
import com.exception.CartException;
import com.exception.UserException;
import com.mapper.CartMapper;
import com.mapper.UserMapper;
import com.model.Cart;
import com.model.User;
import com.repository.CartRepository;
import com.repository.UserRepository;
import com.util.LogUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CartMapper cartMapper;
    private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Override
    public CartDTO createCart(UserDTO userDto) throws CartException, DataAccessException {
        try {
            Optional<User> optionalUser = userRepository.findUserByUserId(userDto.getUserId());
            if (!optionalUser.isPresent()) {
                throw new CartException("User not found");
            }

            int totalPrice = 0;
            int totalDiscountedPrice = 0;
            int totalItem = 0;

            UserDTO user = userMapper.toUserDto(optionalUser.get());
            CartDTO cart = new CartDTO();

            cart.setUserId(user.getUserId());
            cart.setTotalDiscountedPrice(totalDiscountedPrice);
            cart.setTotalItems(totalItem);
            cart.setTotalPrice(totalPrice);
            this.cartRepository.save(cartMapper.toCart(cart));
            return cart;
        } catch (DataAccessException e) {
            logger.error("Data access error while finding cart for user with ID: {}", e);
            throw new CartException("An unexpected error occurred while retrieving the cart for user with ID: ", e);
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
    public CartDTO getUserCart(UserDTO user) throws CartException {
        try {
            CartDTO cart;
            logger.debug("Fetching cart for user: {}", user);
            Optional<Cart> optionalCart = cartRepository.findByUserId(user.getUserId());
            if (!optionalCart.isPresent()) {
                logger.debug("User has no cart. Creating Cart instead");
                cart = this.createCart(user);
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
            int newTotalPrice = 0;
            int newTotalItems = 0;
            int newTotalDiscountedPrice = 0;

            for (CartItemDTO cartItem : cartItems) {
                newTotalItems += cartItem.getQuantity();
                newTotalPrice += (cartItem.getDiscountedPrice() * cartItem.getQuantity());
                newTotalDiscountedPrice += (cartItem.getPrice() * cartItem.getQuantity());
            }
            cart.setTotalDiscountedPrice(newTotalItems);
            cart.setTotalItems(newTotalPrice);
            cart.setTotalPrice(newTotalDiscountedPrice);
            cart.setCartItems(cartItems);
            this.cartRepository.save(cartMapper.toCart(cart));
            return cart;

        } catch (DataAccessException e) {
            e.getStackTrace();
            throw new CartException("Cart not found");
        }
    }

}
