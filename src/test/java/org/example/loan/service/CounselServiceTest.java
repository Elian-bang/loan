package org.example.loan.service;

import static org.example.loan.dto.CounselDTO.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.example.loan.domain.Counsel;
import org.example.loan.dto.CounselDTO;
import org.example.loan.repository.CounselRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class CounselServiceTest {

    @InjectMocks
    CounselServiceImpl counselService;

    @Mock
    private CounselRepository counselRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewCounselEntity_WhenRequestCounsel() {
        Counsel entity = Counsel.builder()
                .name("Name")
                .cellPhone("010-1111-2222")
                .email("abcde@naver.com")
                .memo("저는 대출을 받고 싶어요.")
                .zipCode("12345")
                .address("사랑시 고백구 행복동")
                .addressDetail("123-123")
                .build();

        Request request = Request.builder()
                .name("Name")
                .cellPhone("010-1111-2222")
                .email("abcde@naver.com")
                .memo("저는 대출을 받고 싶어요.")
                .zipCode("12345")
                .address("사랑시 고백구 행복동")
                .addressDetail("123-123")
                .build();

        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);

        Response actual = counselService.create(request);

        assertThat(actual.getName()).isSameAs(entity.getName());
    }
}
