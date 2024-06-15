package com.service;

import com.dto.CartDTO;
import com.dto.CartItemDTO;
import com.dto.ProductDTO;
import com.exception.CartItemException;
import com.exception.UserException;
import com.mapper.CartItemMapper;
import com.model.Cart;
import com.model.CartItem;
import com.model.Product;
import com.model.User;
import com.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService{

    private CartItemRepository cartItemRepository;
    private UserService userService;
    private CartItemMapper mapper;



    @Override
    public CartItemDTO createCartItem(CartItem cartItem) {

        cartItem.setQuantity(cartItem.getQuantity());
        cartItem.setPrice(cartItem.getPrice());

        cartItem.setPrice(cartItem.getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getDiscountedPrice()*cartItem.getQuantity());

        CartItem createdCartItem=cartItemRepository.save(cartItem);
        CartItemDTO cartItemDto = mapper.toCartItemDTO(createdCartItem);
        ///TODO This is a mess. Make sure this works properly.
        
        return cartItemDto;
    }

    @Override
    public CartItemDTO updateCartItem(Long userId, Long cartId, CartItemDTO cartItemDto) throws CartItemException, UserException {

        CartItem item=findCartItemById(cartId);
        User user=userService.findUserById(item.getUserId());
        if(user.getUserId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
        }
        cartItemRepository.save(item);
        
        return findCartItemById(cartId)
    }

    @Override
    public CartItem doesCartItemExist(CartDTO cartDto, ProductDTO productDto, String size, Long userId) {

        return cartItemRepository.doesCartItemExist(cart, product, size, userId);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

        CartItem cartItem=findCartItemById(cartItemId);
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
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt =cartItemRepository.findById(cartItemId);
        if(opt.isPresent()) {
            return opt.get();
        }throw new CartItemException(" cartItem not found with id : " + cartItemId);
    }

    @Override
    public CartItem doesCartItemExist(Cart cart, Product product, String size, Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doesCartItemExist'");
    }
}
