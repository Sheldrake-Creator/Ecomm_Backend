package com.service;
import com.dto.CartDTO;
import com.dto.UserDTO;
import com.exception.CartException;
import com.exception.ProductException;
import com.mapper.CartItemMapper;
import com.mapper.CartMapper;
import com.model.Cart;
import com.model.CartItem;
import com.model.Product;
import com.model.User;
import com.repository.CartRepository;
import com.repository.ProductRepository;
import com.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;
    @SuppressWarnings("unused")
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private CartMapper cartMapper;



    @Override
    public CartDTO createCart(UserDTO userDto) throws CartException{
        if(userDto == null){ 
            throw new IllegalArgumentException("User with ID " + userDto.getUserId() + " not found.");
        }
        System.out.println("UserDTO "+ userDto);
        System.out.println("UserId ="+ userDto.getUserId());
        int totalPrice=0;
        int totalDiscountedPrice=0;
        int totalItem=0;
        Cart cart=new Cart();
        User user =userRepository.findUserByUserId(userDto.getUserId());
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


        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(productId);
        CartItem isPresent = cartItemService.doesCartItemExist(cart,product,size,userId);

        if(isPresent==null) {
            CartItem cartItem = new CartItem();
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
            this.cartRepository.save(cart);
        }

        return "Item Added To Cart";
    }
    @Override
    public Cart findUserCart(Long userId) {

        Cart cart=cartRepository.findByUserId(userId);
        int totalprice=0;
        int totalDiscountedPrice=0;
        int totalItems=0;

        for(CartItem cartItem :cart.getCartItems()) {
            totalprice += (cartItem.getPrice() * cartItem.getQuantity());
            totalDiscountedPrice += (cartItem.getDiscountedPrice() * cartItem.getQuantity());
            totalItems += (cartItem.getQuantity());

        }
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItems(totalItems);
        cart.setTotalPrice(totalprice);
        return cartRepository.save(cart);
    }

    @Override
    public CartDTO getUserCart(UserDTO user) throws CartException {
        Cart cart = this.cartRepository.findByUserId(user.getUserId());
        return cartMapper.toCartDTO(cart);
    }
}
