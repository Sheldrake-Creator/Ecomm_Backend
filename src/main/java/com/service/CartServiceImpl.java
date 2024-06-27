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
import com.exception.CartItemException;
import com.exception.ProductException;
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

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        Optional<User> optionalUser = userRepository.findUserByUserId(userDto.getUserId());
        if (optionalUser == null) {
            throw new CartException("UserId not found ");
        }
        User user = optionalUser.get();

        Cart cart = new Cart();

        cart.setUser(user);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItems(totalItem);
        cart.setTotalPrice(totalPrice);
        this.cartRepository.save(cart);
        return cartMapper.toCartDTO(cart);
    }

    @Override
    public String addItemToCart(Long userId, int quantity, String size, long productId)
            throws ProductException, CartException, CartItemException {

        try {
            CartDTO cart = this.findUserCart(userId);
            logger.debug("CARTSERVICE cartDTO: {}", cart);
            ProductDTO product = productService.findProductById(productId);
            logger.debug("CARTSERVICE ProductDTO: {}", product);
            Optional<CartItem> cartItemEntity = cartItemService.doesCartItemExist(cart, product, size, userId);
            logger.debug("CARTSERVICE CartItemDTO: {}", cartItemEntity);
            
            if(!cartItemEntity.isPresent()){
                CartItemDTO cartItem = new CartItemDTO();
                cartItem.setProduct(product);
                cartItem.setCartId(cart.getCartId());
                cartItem.setQuantity(quantity);
                cartItem.setUserId(userId);
                int price = quantity * product.getPrice();
                int discountedPrice = quantity * product.getDiscountedPrice();
                cartItem.setPrice(price);
                cartItem.setDiscountedPrice(discountedPrice);
                cartItem.setSize(size);
                logger.debug(" CARTSERVICE cartITEM DTO: {}", cartItem);
                cart.getCartItems().add(cartItem);
                this.cartRepository.save(cartMapper.toCart(cart));

            }else{
                return "Item already exists in cart";
            }

            return "Item Added To Cart";
        } catch (CartException e) {
            throw new CartException("Error creating Cart",e);

        } catch (ProductException e) {
            throw new CartException("Error find Product with Product Id",e);

        } catch (CartItemException e) {
            throw new CartException("Error finding CartItem that matches ProductId",e);

        }
    }

    @Override
    public CartDTO findUserCart(Long userId) throws CartException {
        try {
            logger.debug("Finding cart for user with ID: {}", userId);
            Optional<Cart> optionalCart = cartRepository.findByUserId(userId);

            // Handle case when cart is not found
            if (!optionalCart.isPresent()) {
                throw new CartException("UserId not found ");
            }
            
            logger.debug("Mapping Explosion!:");
            Cart cart = optionalCart.get();

            int totalprice = 0;
            int totalDiscountedPrice = 0;
            int totalItems = 0;
            logger.debug("Right Before The loop:");
            for (CartItem cartItem : cart.getCartItems()) {
                totalprice += (cartItem.getPrice() * cartItem.getQuantity());
                totalDiscountedPrice += (cartItem.getDiscountedPrice() * cartItem.getQuantity());
                totalItems += cartItem.getQuantity();
                cartItem.setCart(cart);
            }
            logger.debug("Right After The loop:");

            cart.setTotalDiscountedPrice(totalDiscountedPrice);
            cart.setTotalItems(totalItems);
            cart.setTotalPrice(totalprice);
         
            logger.debug("userID inside of Cart Entity: {}");
            cartRepository.save(cart);


            return cartMapper.toCartDTO(cart);

        } catch (DataAccessException e) {
            logger.error("Data access error while finding cart for user with ID: {}", userId, e);
            throw new CartException("An unexpected error occurred while retrieving the cart for user with ID: ", e);
        }
    }

    @Override
    public CartDTO getUserCart(UserDTO user) throws CartException {
        try {

            logger.debug("Fetching cart for user: {}", user);
            Optional<Cart> optionalCart = cartRepository.findByUserId(user.getUserId());
            if (!optionalCart.isPresent()) {
                logger.debug("User has no cart. Creating Cart instead");
                CartDTO cart = this.createCart(user);
                return cart;
            }
            Cart cart = optionalCart.get();
            return cartMapper.toCartDTO(cart);
        } catch (DataAccessException e) {
            logger.error("Unexpected error occurred while getting user cart: ", e);
            throw new CartException("Unexpected error occurred while getting user cart", e);
        }
    }
}
