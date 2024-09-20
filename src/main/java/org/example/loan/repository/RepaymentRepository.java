package org.example.loan.repository;

import org.example.loan.domain.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepaymentRepository extends JpaRepository<Repayment, Long> {

    List<Repayment> findAllByApplicationId(Long applicationId);
}
