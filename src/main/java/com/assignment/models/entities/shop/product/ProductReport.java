/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.assignment.models.entities.shop.product;

import java.time.LocalDateTime;

import com.assignment.models.entities.auth.User;
import com.assignment.models.entities.staff.Moderators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_reports")
public class ProductReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // lý do báo cáo
    @JoinColumn(name = "reason")
    private String reason;

    // nội dung báo cáo
    @JoinColumn(name = "content")
    private String content;

    // trạng thái báo cáo
    @JoinColumn(name = "status")
    private ReportStatus status = ReportStatus.PENDING; // pending, rejected, processed

    // ngày báo cáo
    @JoinColumn(name = "report_at")
    private LocalDateTime reportAt = LocalDateTime.now();

    // ngày xử lý báo cáo
    @JoinColumn(name = "process_at")
    private LocalDateTime processAt = null;

    // người xử lý báo cáo
    @ManyToOne
    @JoinColumn(name = "process_by")
    private Moderators processBy = null; 

    // nội dung xử lý báo cáo
    @JoinColumn(name = "process_content")
    private String processContent = null;


}
