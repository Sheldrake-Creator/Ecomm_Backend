package com.service;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.CartDTO;
import com.dto.CartItemDTO;
import com.dto.ProductDTO;
import com.exception.CartException;
import com.exception.CartItemException;
import com.exception.ProductException;
import com.mapper.CartItemMapper;
import com.mapper.CartMapper;
import com.mapper.ProductMapper;
import com.model.CartItem;
import com.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService {

    private final ProductService productService;
    private final CartService cartService;
    private final CartItemMapper cartItemMapper;
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    private final CartItemRepository cartItemRepository;
    private final Logger logger = LoggerFactory.getLogger(CartItemServiceImpl.class);

    @Override
    public CartItemDTO updateCartItem(Long cartItemId, CartItemDTO newCartItemDto) throws CartItemException {
        try {

            logger.debug("cartItemId: {}", cartItemId);
            logger.debug("newCartItemDto: {}", newCartItemDto);

            CartItem existingCartItem = cartItemRepository.findCartItemById(cartItemId)
                    .orElseThrow(() -> new CartItemException("CartItem not found with id: " + cartItemId));
            logger.debug("existingCartItem: {}", existingCartItem);

            existingCartItem.setQuantity(newCartItemDto.getQuantity());
            existingCartItem.setPrice(existingCartItem.getProduct().getPrice() * newCartItemDto.getQuantity());
            existingCartItem.setDiscountedPrice(
                    existingCartItem.getProduct().getDiscountedPrice() * newCartItemDto.getQuantity());
            logger.debug("existingCartItem: {}", existingCartItem);

            cartItemRepository.save(existingCartItem);

            CartDTO cart = this.cartService.findCartByCartId(existingCartItem.getCart().getCartId());
            logger.debug("cart: {}", cart);
            this.cartService.syncCartWithCartItems(cart);
            CartItemDTO newCartItem = cartItemMapper.toCartItemDTO(existingCartItem);
            logger.debug("cart: {}", newCartItem);

            return newCartItem;
        } catch (DataAccessException e) {
            throw new CartItemException("CartItem Exception Thrown", e);
        } catch (CartException e) {
            throw new CartItemException("CartItem Exception Thrown", e);
        }
    }

    @Override
    public CartDTO removeCartItem(Long userId, Long cartItemId) throws CartItemException {
        try {
            this.cartItemRepository.deleteCartItemById(cartItemId);
            CartDTO cart = cartService.findUserCart(userId);
            CartDTO newCart = this.cartService.syncCartWithCartItems(cart);
            return newCart;
        } catch (CartException e) {
            throw new CartItemException("Cart Item not Found", e);
        }
    }

    @Override
    @Transactional
    public boolean doesCartItemExist(CartDTO cart, ProductDTO product, String size) throws CartItemException {
        try {
            logger.debug("CARTITEMSERVICE: {}", cart);
            Optional<CartItem> cartItem = this.cartItemRepository.doesCartItemExist(cartMapper.toCart(cart),
                    productMapper.toProduct(product), size);
            logger.debug("CARTITEMSERVICE: {}", cartItem);

            return cartItem.isPresent();
        } catch (DataAccessException e) {
            logger.error("Data access error while finding cart for user with ID: {}", e);
            throw new CartItemException("An unexpected error occurred while retrieving the cart for user with ID: ", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CartItemDTO findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);

        if (!opt.isPresent()) {
            throw new IllegalArgumentException("cartItemId does not exist in DB");
        }
        CartItemDTO cartItem = cartItemMapper.toCartItemDTO(opt.get());
        // CartDTO cart = cartService.findUserCart(cartItem.getCartId());

        return cartItem;

    }

    @Override
    public CartDTO addItemToCart(Long userId, Integer quantity, String size, long productId) throws CartItemException {
        try {

            CartDTO existingCart = cartService.findUserCart(userId);
            logger.debug("Existing Cart: {}", existingCart);

            ProductDTO product = productService.findProductById(productId);
            logger.debug("ProductDTO: {}", product);

            if (!this.doesCartItemExist(existingCart, product, size)) {
                // *This is Definitely the issue. This is definitely throwing a null value.

                Set<CartItemDTO> existingCartItems = existingCart.getCartItems();
                logger.debug("existingCartItems: {}", existingCartItems);
                CartItemDTO newCartItem = new CartItemDTO();

                newCartItem.setProduct(product);
                newCartItem.setCartId(existingCart.getCartId());
                logger.debug("cartId: {}", existingCart.getCartId());
                newCartItem.setQuantity(quantity);
                Integer price = (quantity * product.getPrice());
                Integer discountedPrice = (quantity * product.getDiscountedPrice());
                newCartItem.setPrice(price);
                newCartItem.setDiscountedPrice(discountedPrice);
                newCartItem.setSize(size);
                logger.debug(" CARTSERVICE cartITEM DTO: {}", newCartItem);

                existingCartItems.add(newCartItem);

                existingCart.setCartItems(existingCartItems);
                CartDTO newCart = cartService.syncCartWithCartItems(existingCart);
                return newCart;
            }
            throw new CartItemException("CartItem Already Exists in Cart");

        } catch (ProductException e) {
            throw new CartItemException("Error find Product with Product Id: ", e);

        } catch (CartItemException e) {
            throw new CartItemException("Error finding CartItem that matches ProductId", e);

        } catch (CartException e) {
            // TODO Auto-generated catch block
            throw new CartItemException("Cart Error", e);
        }

    }

    // * Helper Methods
    // private void updateCartTotals(Cart cart, CartItem cartItem) {
    // cart.setTotalItems(cart.getTotalItems() + cartItem.getQuantity());
    // cart.setTotalPrice(cart.getTotalPrice() + cartItem.getProduct().getPrice() *
    // cartItem.getQuantity());
    // cart.setTotalDiscountedPrice(
    // cart.getTotalDiscountedPrice() + cartItem.getProduct().getDiscountedPrice() *
    // cartItem.getQuantity());
    // }
}
