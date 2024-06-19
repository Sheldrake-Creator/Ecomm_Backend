package com.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.CartDTO;
import com.dto.CartItemDTO;
import com.dto.ProductDTO;
import com.exception.CartItemException;
import com.exception.UserException;
import com.mapper.CartItemMapper;
import com.mapper.CartMapper;
import com.mapper.ProductMapper;
import com.model.Cart;
import com.model.CartItem;
import com.model.User;
import com.repository.CartItemRepository;
import com.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService{

    private CartItemRepository cartItemRepository;
    private UserService userService;
    private CartItemMapper cartItemMapper;
    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private ProductMapper productMapper;



    @Override
    public CartItemDTO addCartItem(CartItem cartItem, long userId) {
        Cart cartEntity = cartRepository.findByUserId(userId);



        cartEntity.setTotalItems(cartEntity.getTotalItems()+ cartItem.getQuantity());
        cartEntity.setTotalPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartEntity.setTotalDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());


        Set<CartItem> cartItems = cartEntity.getCartItems();
        cartItems.add(cartItem);
        cartEntity.setCartItems(cartItems);;
        this.cartRepository.save(cartEntity);

        // CartItem createdCartItem=cartItemRepository.save(cartItem);
        CartItemDTO cartItemDto = cartItemMapper.toCartItemDTO(cartItem);
        return cartItemDto;
        

    }

    @Override
    public CartItemDTO updateCartItem(Long userId, Long cartId, CartItemDTO cartItemDto) throws CartItemException, UserException {

        CartItemDTO item=findCartItemById(cartId);
        User user=userService.findUserById(item.getUserId());
        if(user.getUserId().equals(userId)){
            item.setQuantity(cartItemDto.getQuantity());
            item.setPrice(item.getQuantity()*item.getPrice());
            item.setDiscountedPrice(item.getDiscountedPrice()*item.getQuantity());
        }
        cartItemRepository.save(cartItemMapper.toCartItem(item));
        
        return findCartItemById(cartId);
    }

    @Override
    public CartItemDTO doesCartItemExist(CartDTO cart, ProductDTO product, String size, Long userId) {
        CartItem cartItem= this.cartItemRepository.doesCartItemExist(cartMapper.toCart(cart),productMapper.toProduct(product), size, userId);
        return cartItemMapper.toCartItemDTO(cartItem);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

        CartItemDTO cartItem=findCartItemById(cartItemId);
        User user=userService.findUserById(cartItem.getUserId());
        User reqUser=userService.findUserById(userId);

        if(user.getUserId().equals(reqUser.getUserId())) {
            cartItemRepository.deleteById(cartItemId);
        }
        else {
        throw new UserException("You can't remove another users item");
        }
    }

    @Override
    public CartItemDTO findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt =cartItemRepository.findById(cartItemId);
        if(opt.isPresent()) {
            CartItemDTO cartItem = cartItemMapper.toCartItemDTO(opt.get());
            return cartItem;
        }throw new CartItemException(" cartItem not found with id : " + cartItemId);
    }
}
