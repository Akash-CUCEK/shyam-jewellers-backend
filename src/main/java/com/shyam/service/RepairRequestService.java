package com.shyam.service;

import com.shyam.dto.request.*;
import com.shyam.dto.response.*;

public interface RepairRequestService {
    GetAllRepairResponseDTO getAllRepairRequests(String requestId);

    CreateRepairResponseDTO createRepairRequest(String requestId, CreateRepairRequestDTO createRepairRequestDTO);

    EditRepairResponseDTO editRepairRequest(String requestId, EditRepairRequestDTO editRepairRequestDTO);

    GetAllRepairResponseDTO searchRepairRequest(String requestId, SearchRepairRequestDTO createRepairRequestDTO);

    DeleteRepairResponseDTO deleteRepairRequest(String requestId, DeleteRepairRequestDTO deleteRepairRequestDTO);

    RepairRequestResponseDTO getRepairRequestById(String requestId, RepairRequestRequestDTO repairRequestRequestDTO);
}
