package com.response;

import com.dto.CartDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class GetCartResponse implements CartResponse{

   @Getter @Setter private CartDTO cart;


}

