package myapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import myapp.entity.*;
import myapp.repository.*;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private CartRepository cartRepository;
    
    public Page<Menu> getMenuByCategory(String category,int page, int size) {
    	PageRequest pageRequest = PageRequest.of(page, size);
        return menuRepository.findByPage(category, pageRequest);
    }
    
    public boolean addToCart(String menu_Name) {
    	Optional<Menu> menuItem = menuRepository.findById(menu_Name);
    	
    	if (menuItem.isPresent()) {
            Menu menu = menuItem.get();

            // Cart 엔티티 생성 후 저장
            Cart cart =new Cart(menu.getMenuName(),menu.getMenuNameen(),menu.getMenu(),menu.getPrice());  // 예: 기본적으로 1개의 수량 저장
            cartRepository.save(cart);

            return true;  // 저장 성공 시 true 반환
        } else {
            return false;  // Menu 테이블에서 해당 메뉴를 찾지 못한 경우
        }
    }
}
