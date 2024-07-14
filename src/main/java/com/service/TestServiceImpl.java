package com.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exception.CartServiceException;
import com.exception.CartItemException;
import com.exception.RatingServiceException;
import com.exception.ReviewServiceException;
import com.exception.UserServiceException;

// import java.util.List;
// import java.util.Optional;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import com.dto.ProductDTO;
// import com.exception.CartServiceException;
// import com.exception.CartItemException;
// import com.exception.RatingServiceException;
// import com.exception.ReviewServiceException;
// import com.exception.UserServiceException;
// import com.mapper.CartItemMapper;
// import com.mapper.CartMapper;
// import com.mapper.ProductMapper;
// import com.mapper.UserMapper;
// import com.model.Product;
// import com.repository.CartItemRepository;
// import com.repository.CartRepository;
// import com.repository.OrderItemRepository;
// import com.repository.OrderRepository;
// import com.repository.ProductRepository;
// import com.repository.ReviewRepository;
// import com.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TestServiceImpl implements TestService {
    // Repos
    // private final UserRepository userRepository;
    // private final CartRepository cartRepository;
    // private final CartItemRepository cartItemRepository;
    // private final ProductRepository productRepository;
    // private final OrderRepository orderRepository;
    // private final OrderItemRepository orderItemRepository;
    // private final ReviewRepository reviewsRep;
    // private final ReviewRepository RatingsRep;

    // // Services
    // private final UserService userService;
    // private final CartService cartService;
    // private final CartItemService cartItemService;
    // private final ProductService productService;
    // private final OrderService orderService;
    // private final ReviewService reviewService;
    // private final RatingService ratingService;

    // private final UserMapper userMapper;
    // private final CartMapper cartMapper;
    // private final CartItemMapper cartItemMapper;
    // private final ProductMapper productMapper;
    // private final CartService cartServices;
    // private final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);
    // private ProductRepository productRepository;

    public void testEverything(String jwt) throws UserServiceException, CartServiceException, CartItemException,
            ReviewServiceException, RatingServiceException {
        // Optional<Product> product = this.productRepository.findById(id);
        // // logger.debug("Product: {}", product.get());

        // ProductMapper productMapper;
        // ProductDTO prodDTO = productMapper.toProductDTO(product.get());
        // logger.debug("ProductDTO: {}", prodDTO);

        // System.out.println("CALLING User");
        // UserDTO user = this.userService.findUserProfileByJwt(jwt);
        // System.out.println("CALLING CART");
        // CartDTO cartDTO = this.cartService.findUserCart(user.getUserId());
        // System.out.println("CALLING CARTITEMS");
        // CartItemDTO cartItem = this.cartItemService.findCartItemById(6L);

        // System.out.println("CALLING CART REPO ENTITY");
        // Optional<Cart> ocartEntity = this.cartRepository.findById(1L);
        // System.out.println("CALLING USER REPO ENTITY");
        // Optional<User> ouserEntity = this.userRepository.findById(5L);
        // System.out.println("CALLING CART ITEM REPO ENTITY");
        // Optional<CartItem> ocartItemEntity = this.cartItemRepository.findById(6L);

        // System.out.println(" ");
        // System.out.println("TESTING SERVICES & DTO CLASSES");
        // System.out.println(" ");
        // logger.debug("Token {}",jwt);
        // System.out.println(" ");

        // System.out.println("User Information");
        // logger.debug("UserDTO {}",user);
        // System.out.println(" ");

        // System.out.println("Cart Information");
        // System.out.println(" ");

        // logger.debug("CartDTO {}",cartDTO);
        // Integer index = 0;
        // for (CartItemDTO cartItemDTO : cartDTO.getCartItems()){
        // logger.debug("CartItemDTO (inside Cart) [{}]: {}", index++, cartItemDTO);}

        // System.out.println(" ");
        // System.out.println("CartItem DTO Information");
        // logger.debug("CartItemDTO {}",cartItem);
        // logger.debug("ProductDTO {}",cartItem.getProduct());

        // System.out.println(" ");
        // System.out.println(" ");
        // System.out.println(" ");
        // System.out.println("REPOSITORY & ENTITY CLASSES");
        // System.out.println(" ");
        // User userEntity = ouserEntity.get();
        // Cart cartEntity = ocartEntity.get();
        // CartItem cartItemEntity = ocartItemEntity.get();

        // logger.debug("UserENTITY {}",userMapper.toUserDto(userEntity));
        // logger.debug("CartEntity {}",cartMapper.toCartDTO(cartEntity));
        // Integer index2 = 0;

        // for (CartItem cartItemloop : cartEntity.getCartItems()) {
        // logger.debug("CartItemDTO (inside Cart) [{}]: {}", index2++,
        // cartItemMapper.toCartItemDTO(cartItemloop));}

        // logger.debug("CartItemEntities
        // {}",cartItemMapper.toCartItemDTO(cartItemEntity));
        // logger.debug("ProductEntity
        // {}",productMapper.toProductDTO(cartItemEntity.getProduct()));

        // logger.info("---");

        // List<ReviewDTO> reviewsDTOs = reviewService.getAllReviews(4L);
        // List<RatingDTO> ratingsDTOs = ratingService.getAllRatings(4L);

        // for(RatingDTO rating : ratingsDTOs){
        // logger.debug("Rating: {}",rating);
        // }

        // for(ReviewDTO review : reviewsDTOs){
        // logger.debug("review: {}",review);

        // }
    }
}
