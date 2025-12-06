package com.shyam.controller;

import com.shyam.common.exception.dto.BaseResponseDTO;
import com.shyam.dto.request.*;
import com.shyam.dto.response.*;
import com.shyam.service.RepairRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/common")
@RequiredArgsConstructor
@Slf4j
public class RepairRequestController {
    private final RepairRequestService repairRequestService;

    @PostMapping("/getAllRepairRequests")
    public BaseResponseDTO<GetAllRepairResponseDTO> getAllRepairRequests(@RequestHeader String requestId) {
        log.info("Received request for getting all repair requests for requestId : {}",requestId);
        var response = repairRequestService.getAllRepairRequests(requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }
    @PostMapping("/searchRepairRequest")
    public BaseResponseDTO<GetAllRepairResponseDTO> searchRepairRequest(
            @RequestHeader String requestId,
            @RequestBody SearchRepairRequestDTO createRepairRequestDTO
    ) {
        log.info("Received request for search repair request for requestId : {}",requestId);
        var response = repairRequestService.searchRepairRequest(requestId,createRepairRequestDTO);
        return new BaseResponseDTO<>(response,null,requestId);
    }
    @PostMapping("/getRepairRequestById")
    public BaseResponseDTO<RepairRequestResponseDTO> getAllRepairRequests(
            @RequestHeader String requestId,
            @RequestBody RepairRequestRequestDTO repairRequestRequestDTO

    ) {
        log.info("Received request for getting repair request for requestId : {}",requestId);
        var response = repairRequestService.getRepairRequestById(requestId,repairRequestRequestDTO);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @PostMapping("/createRepairRequest")
    public BaseResponseDTO<CreateRepairResponseDTO> createRepairRequest(
            @RequestHeader String requestId,
            @RequestBody CreateRepairRequestDTO createRepairRequestDTO
    ) {
        log.info("Received request for create repair request for requestId : {}",requestId);
        var response = repairRequestService.createRepairRequest(requestId,createRepairRequestDTO);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @PutMapping("/editRepairRequest")
    public BaseResponseDTO<EditRepairResponseDTO> createRepairRequest(
            @RequestHeader String requestId,
            @RequestBody EditRepairRequestDTO editRepairRequestDTO
    ) {
        log.info("Received request for edit repair request for requestId : {}",requestId);
        var response = repairRequestService.editRepairRequest(requestId,editRepairRequestDTO);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @DeleteMapping("/deleteRepairRequest")
    public BaseResponseDTO<DeleteRepairResponseDTO> deleteRepairRequest(
            @RequestHeader String requestId,
            @RequestBody DeleteRepairRequestDTO deleteRepairRequestDTO
    ) {
        log.info("Received request for delete repair request for requestId : {}",requestId);
        var response = repairRequestService.deleteRepairRequest(requestId,deleteRepairRequestDTO);
        return new BaseResponseDTO<>(response,null,requestId);
    }

}
