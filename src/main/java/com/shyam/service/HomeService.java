package com.shyam.service;

import com.shyam.dto.request.*;
import com.shyam.dto.response.*;

public interface HomeService {
    GetAllHomeServiceResponseDTO getAllHomeServiceRequests(String requestId);

    CreateHomeServiceResponseDTO createHomeServiceRequests(String requestId, CreateHomeServiceRequestDTO createHomeServiceRequestDTO);

    EditHomeServiceResponseDTO editHomeServiceRequest(String requestId, EditHomeServiceRequestDTO editHomeServiceRequestDTO);

    GetAllHomeServiceResponseDTO searchHomeServiceRequest(String requestId, SearchHomeServiceRequestDTO searchHomeServiceRequestDTO);

    DeleteHomeServiceResponseDTO deleteHomeServiceRequest(String requestId, DeleteHomeServiceRequestDTO editHomeServiceRequestDTO);

    HomeServiceResponseDTO getHomeServiceRequestById(String requestId, HomeServiceRequestDTO homeServiceRequestDTO);

    GetAllHomeServiceResponseDTO getAllUserServiceRequests(String requestId);
}
