package com.ceos20.spring_boot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor  // 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해주어 생성자를 통한 의존성 주입을 직접 구현할 필요 없음
public class TestService {
    private final TestRepository testRepository;

    /* Read All */
    @Transactional(readOnly=true)  // db에서 데이터를 읽기만 하는 서비스 메소드에 적용 (readOnly를 설정하면 성능이 향상됨)
    public List<Test> findAllTest(){
        return testRepository.findAll();
    }
}
