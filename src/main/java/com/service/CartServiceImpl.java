package com.service;
import com.dto.CartDTO;
import com.dto.CartItemDTO;
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
    private CartItemMapper cartItemMapper;

    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService,
                           ProductRepository productRepository, ProductService productService,
                           UserRepository userRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productRepository = productRepository;
        this.productService= productService;
        this.userRepository=userRepository;
        this.cartMapper=cartMapper;
        this.cartItemMapper=cartItemMapper;
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
    public String addItemToCart(AddItemRequest req) throws ProductException {
        System.out.println("Request "+req);
        System.out.println("User "+req.getUser());
        System.out.println("Product "+req.getProduct());
        System.out.println("CartItem "+req.getCartItem());

        Cart cart = cartRepository.findByUserId(req.getUser().getUserId());
        Product product = productService.findProductById(req.getProduct().getProductId());
        CartItem isPresent = cartItemService.doesCartItemExist(cart,product,req.getCartItem().getSize(),req.getUser().getUserId());

        if(isPresent==null) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getCartItem().getQuantity());
            cartItem.setUserId(req.getUser().getUserId());
            int price = req.getCartItem().getQuantity() * product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getCartItem().getSize());
            cartItemService.createCartItem(cartItem);
            
            cart.getCartItems().add(cartItem);
        }
        return "Item Added To Cart";
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
            totalItem = totalItem+cartItem.getQuantity();
        }

        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItems(totalItem);
        cart.setTotalPrice(totalPrice);
        return cartRepository.save(cart);
    }

    @Override
    public CartDTO getUserCart(UserDTO user) throws CartException {
        Cart cart = this.cartRepository.findByUserId(user.getUserId());
        return cartMapper.toCartDTO(cart);

    }
}
