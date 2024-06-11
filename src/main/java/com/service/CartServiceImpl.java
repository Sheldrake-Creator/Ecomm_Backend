package com.service;

import com.dto.CartDTO;
import com.dto.UserDTO;
import com.exception.ProductException;
import com.mapper.CartMapper;
import com.model.Cart;
import com.model.CartItem;
import com.model.Product;
import com.model.User;
import com.repository.CartRepository;
import com.repository.ProductRepository;
import com.repository.UserRepository;
import com.request.AddItemRequest;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;
    @SuppressWarnings("unused")
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService,
                           ProductRepository productRepository, ProductService productService,
                           UserRepository userRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productRepository = productRepository;
        this.productService= productService;
        this.userRepository=userRepository;
        this.cartMapper=cartMapper;

    }

    @Override
    public CartDTO createCart(UserDTO userDto) {
        if(userDto==null){
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
    public String addItemToCart(Long userId, AddItemRequest req) throws ProductException {

        Cart cart = cartRepository.findByUserId(userId);
        Product product=productService.findProductById(req.getProductId());
        CartItem isPresent=cartItemService.doesCartItemExist(cart,product,req.getSize(),userId);

        if(isPresent==null) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);
            int price = req.getQuantity() * product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());
            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }
        return "Item Add To Cart";
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart=cartRepository.findByUserId(userId);

        int totalPrice=0;
        int totalDiscountedPrice=0;
        int totalItem=0;

        for(CartItem cartItem :cart.getCartItems()) {
            totalPrice = totalPrice + cartItem.getPrice();
            totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
            totalItem=totalItem+cartItem.getQuantity();
        }

        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItems(totalItem);
        cart.setTotalPrice(totalPrice);
        return cartRepository.save(cart);
    }
}
