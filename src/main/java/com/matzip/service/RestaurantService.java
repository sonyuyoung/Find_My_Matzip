package com.matzip.service;

import com.matzip.dto.*;
import com.matzip.entity.Restaurant;
import com.matzip.entity.RestaurantImg;
import com.matzip.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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

    @Transactional(readOnly = true)
    public Page<MainRestaurantDto> getMainRestaurantPage(RestaurantSearchDto restaurantSearchDto, Pageable pageable){
        return restaurantRepository.getMainRestaurantPage(restaurantSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public RestaurantFormDto getRestaurantDtl(String res_id){
        //해당 게시글의 이미지 조회, 등록순으로 가지고 오기 위해서 게시글이미지 아이디를 오름차순으로 가지고온다
        List<RestaurantImgDto> restaurantImgDtoList = new ArrayList<>();
        //게시글의 아이디를 통해 상품 엔티티를 조회 . 존재하지않으면 오류 발생시키기
        Restaurant restaurant = restaurantRepository.findById(res_id)
                .orElseThrow(EntityNotFoundException::new);
        RestaurantFormDto restaurantFormDto = RestaurantFormDto.of(restaurant);
        restaurantFormDto.setRestaurantImgDtoList(restaurantImgDtoList);
        return restaurantFormDto;
    }


}
