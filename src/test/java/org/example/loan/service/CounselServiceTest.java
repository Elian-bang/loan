package org.example.loan.service;

import static org.example.loan.dto.CounselDTO.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.example.loan.domain.Counsel;
import org.example.loan.exception.BaseException;
import org.example.loan.exception.ResultType;
import org.example.loan.repository.CounselRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

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


    @Test
    void Should_ReturnResponseOfExistingCounselEntity_WhenRequestExistingCounselId() {
        Long findId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .build();

        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = counselService.get(findId);
        assertThat(actual.getCounselId()).isSameAs(findId);
    }

    @Test
    void Should_ThrowException_WhenRequestNotExistCounselId() {
        Long findId = 2L;

        when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

        Assertions.assertThrows(BaseException.class, () -> counselService.get(findId));

    }
}
