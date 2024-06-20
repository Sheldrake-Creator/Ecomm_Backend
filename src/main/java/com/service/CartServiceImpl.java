package com.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.dto.CartDTO;
import com.dto.CartItemDTO;
import com.dto.ProductDTO;
import com.dto.UserDTO;
import com.exception.CartException;
import com.exception.ProductException;
import com.exception.UserException;
import com.mapper.CartMapper;
import com.model.Cart;
import com.model.CartItem;
import com.model.User;
import com.repository.CartRepository;

import com.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;
    private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Override
    public CartDTO createCart(UserDTO userDto) throws CartException {
        if (userDto == null) {
            throw new IllegalArgumentException("User with ID not found.");
        }
        System.out.println("UserDTO " + userDto);
        System.out.println("UserId =" + userDto.getUserId());
        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;
        Cart cart = new Cart();
        User user = userRepository.findUserByUserId(userDto.getUserId());
        cart.setUser(user);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItems(totalItem);
        cart.setTotalPrice(totalPrice);
        this.cartRepository.save(cart);
        return cartMapper.toCartDTO(cart);
    }

    @Override
    public String addItemToCart(Long userId, int quantity, String size, long productId) throws ProductException {
        // System.out.println("Request "+);
        // System.out.println("User "+);
        // System.out.println("Product "+);
        // System.out.println("CartItem "+);

        CartDTO cart = this.findUserCart(userId);
        ProductDTO product = productService.findProductById(productId);
        CartItemDTO isPresent = cartItemService.doesCartItemExist(cart, product, size, userId);

        if (isPresent == null) {
            CartItemDTO cartItem = new CartItemDTO();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cartItem.setUserId(userId);
            int price = quantity * product.getPrice();
            int discountedPrice = quantity * product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setDiscountedPrice(discountedPrice);
            cartItem.setSize(size);
            cart.getCartItems().add(cartItem);
            this.cartRepository.save(cartMapper.toCart(cart));
        }

        return "Item Added To Cart";
    }

    @Override
    public CartDTO findUserCart(Long userId) throws CartException {
        try {
            logger.debug("Finding cart for user with ID: {}", userId);
            Optional<Cart> optionalCart = cartRepository.findByUserId(userId);

            // Handle case when cart is not found
            if (!optionalCart.isPresent()) {

            }

            Cart cart = optionalCart.get();
            int totalprice = 0;
            int totalDiscountedPrice = 0;
            int totalItems = 0;

            for (CartItem cartItem : cart.getCartItems()) {
                totalprice += (cartItem.getPrice() * cartItem.getQuantity());
                totalDiscountedPrice += (cartItem.getDiscountedPrice() * cartItem.getQuantity());
                totalItems += cartItem.getQuantity();
            }

            cart.setTotalDiscountedPrice(totalDiscountedPrice);
            cart.setTotalItems(totalItems);
            cart.setTotalPrice(totalprice);
            cartRepository.save(cart);

            logger.debug("Cart details: {}", cart);
            return cartMapper.toCartDTO(cart);

        } catch (DataAccessException e) {
            logger.error("Data access error while finding cart for user with ID: {}", userId, e);
            throw new CartException("An unexpected error occurred while retrieving the cart for user with ID: ", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while finding cart for user with ID: {}", userId, e);

        }
    }

    @Override
    public CartDTO getUserCart(UserDTO user) throws CartException {
        try {
            logger.debug("Fetching cart for user: {}", user);
            Optional<Cart> optionalCart = cartRepository.findByUserId(user.getUserId());
            if (optionalCart == null) {

            }
            Cart cart = optionalCart.get();
            return cartMapper.toCartDTO(cart);
        } catch (DAOException e) {
            logger.error("Unexpected error occurred while getting user cart: ", e);
            throw new DAOException("Unexpected error occurred while getting user cart", e);
        }
    }
}
