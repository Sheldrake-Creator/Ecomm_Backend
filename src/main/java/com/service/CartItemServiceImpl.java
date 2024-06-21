package com.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.CartDTO;
import com.dto.CartItemDTO;
import com.dto.ProductDTO;
import com.dto.UserDTO;
import com.exception.CartItemException;
import com.exception.UserException;
import com.mapper.CartItemMapper;
import com.mapper.CartMapper;
import com.mapper.ProductMapper;
import com.model.Cart;
import com.model.CartItem;
import com.repository.CartItemRepository;
import com.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final CartItemMapper cartItemMapper;
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    private final Logger logger = LoggerFactory.getLogger(CartItemServiceImpl.class);

    public CartItemDTO addCartItem(CartItem cartItem, long userId) throws CartItemException {
        Cart cartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    logger.error("No Cart Found with UserId: {}", userId);
                    return new CartItemException("No Cart Found with UserId: " + userId);
                });

        updateCartTotals(cartEntity, cartItem);
        cartEntity.getCartItems().add(cartItem);
        cartRepository.save(cartEntity);

        return cartItemMapper.toCartItemDTO(cartItem);
    }

    @Override
    public CartItemDTO updateCartItem(Long userId, Long cartId, CartItemDTO cartItemDto)
            throws CartItemException, UserException {

        CartItemDTO item = findCartItemById(cartId);
        UserDTO user = userService.findUserById(item.getUserId());
        if (user.getUserId().equals(userId)) {
            item.setQuantity(cartItemDto.getQuantity());
            item.setPrice(item.getQuantity() * item.getPrice());
            item.setDiscountedPrice(item.getDiscountedPrice() * item.getQuantity());
        }
        cartItemRepository.save(cartItemMapper.toCartItem(item));

        return findCartItemById(cartId);
    }

    @Override
    public CartItemDTO doesCartItemExist(CartDTO cart, ProductDTO product, String size, Long userId)
            throws CartItemException {
        try {
            CartItem cartItem = this.cartItemRepository.doesCartItemExist(
                    cartMapper.toCart(cart),
                    productMapper.toProduct(product),
                    size,
                    userId).orElseThrow(() -> {
                        logger.error("No CartItem Found for UserId: {}, Product: {}, Size: {}", userId,
                                product.getProductId(), size);
                        return new CartItemException("No CartItem Found for UserId: " + userId + ", Product: "
                                + product.getProductId() + ", Size: " + size);
                    });
            return cartItemMapper.toCartItemDTO(cartItem);
        } catch (DataAccessException e) {
            logger.error("Data access error while finding cart for user with ID: {}", userId, e);
            throw new CartItemException("An unexpected error occurred while retrieving the cart for user with ID: ", e);
        }
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

        CartItemDTO cartItem = findCartItemById(cartItemId);
        UserDTO user = userService.findUserById(cartItem.getUserId());
        UserDTO reqUser = userService.findUserById(userId);

        if (user.getUserId().equals(reqUser.getUserId())) {
            cartItemRepository.deleteById(cartItemId);
        } else {
            throw new UserException("You can't remove another users item");
        }
    }

    @Override
    public CartItemDTO findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
        if (opt.isPresent()) {
            CartItemDTO cartItem = cartItemMapper.toCartItemDTO(opt.get());
            return cartItem;
        }
        throw new CartItemException(" cartItem not found with id : " + cartItemId);
    }

    // * Helper Methods
    private void updateCartTotals(Cart cart, CartItem cartItem) {
        cart.setTotalItems(cart.getTotalItems() + cartItem.getQuantity());
        cart.setTotalPrice(cart.getTotalPrice() + cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cart.setTotalDiscountedPrice(
                cart.getTotalDiscountedPrice() + cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
    }
}
