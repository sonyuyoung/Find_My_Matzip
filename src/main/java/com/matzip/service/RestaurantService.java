package com.matzip.service;

import com.matzip.dto.RestaurantDto;
import com.matzip.entity.Restaurant;
import com.matzip.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final FileService fileService;


    /*@Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {

        Users users = usersRepository.findByUserid(userid);

        if(users == null){
            throw new UsernameNotFoundException(userid);
        }

        return User.builder()
                .username(users.getUserid())
                .password(users.getUser_pwd())
                .roles(users.getUser_role().toString())
                .build();
    }*/


    public List<RestaurantDto> findAll(){
        List<Restaurant> restaurantEntityList = restaurantRepository.findAll();
        System.out.println("restaurantDtoList 의 소스 확인 동네 2 서비스: "+restaurantEntityList.get(0).getRes_name());
        List<RestaurantDto> restaurantDtoList = new ArrayList<>();;
        for (Restaurant restaurant : restaurantEntityList) {
            restaurantDtoList.add(RestaurantDto.restaurantDto(restaurant));
        }
        return restaurantDtoList;
    }

    public RestaurantDto findById(String res_id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(res_id);
        if (optionalRestaurant.isPresent()){
            return RestaurantDto.restaurantDto(optionalRestaurant.get());
        } else {
            return null;
        }
    }


}
