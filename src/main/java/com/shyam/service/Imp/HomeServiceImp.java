package com.shyam.service.Imp;

import com.shyam.common.util.MessageSourceUtil;
import com.shyam.dao.HomeServiceDAO;
import com.shyam.dto.request.*;
import com.shyam.dto.response.*;
import com.shyam.entity.ServiceHome;
import com.shyam.mapper.HomeServiceMapper;
import com.shyam.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.shyam.constants.MessageConstant.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class HomeServiceImp implements HomeService {
    private final HomeServiceDAO homeServiceDAO;
    private final MessageSourceUtil messageSourceUtil;

    @Override
    public GetAllHomeServiceResponseDTO getAllHomeServiceRequests(String requestId) {
        List<ServiceHome> services = homeServiceDAO.getAllHomeServiceRequests();
        return HomeServiceMapper.getAllHomeServiceRequests(services);
    }
    @Override
    public GetAllHomeServiceResponseDTO searchHomeServiceRequest(String requestId, SearchHomeServiceRequestDTO searchHomeServiceRequestDTO) {
        List<ServiceHome> services = homeServiceDAO.searchHomeServices(searchHomeServiceRequestDTO.getKeyword());
        return HomeServiceMapper.getAllHomeServiceRequests(services);
    }

    @Override
    public HomeServiceResponseDTO getHomeServiceRequestById(String requestId, HomeServiceRequestDTO homeServiceRequestDTO) {
        var service = homeServiceDAO.findHomeService(homeServiceRequestDTO.getServiceId());
        return HomeServiceMapper.getHomeServiceRequestById(service);
    }

    @Override
    public GetAllHomeServiceResponseDTO getAllUserServiceRequests(String requestId) {
        return null;
    }

    @Override
    public CreateHomeServiceResponseDTO createHomeServiceRequests(String requestId, CreateHomeServiceRequestDTO createHomeServiceRequestDTO) {
        var service = HomeServiceMapper.createHomeServiceRequests(createHomeServiceRequestDTO);
        homeServiceDAO.saveHomeService(service);
        return HomeServiceMapper.maoToCreateHomeServiceResponseDTO(messageSourceUtil
                .getMessage(MESSAGE_CODE_CREATE_HOME_SERVICE));
    }

    @Override
    public EditHomeServiceResponseDTO editHomeServiceRequest(String requestId, EditHomeServiceRequestDTO dto) {
        var service = homeServiceDAO.findHomeService(dto.getServiceId());
        var serviceMapper = HomeServiceMapper.editHomeServiceRequest(service, dto);
        homeServiceDAO.saveHomeService(serviceMapper);
        return HomeServiceMapper.mapTOEditHomeServiceResponseDTO(
                messageSourceUtil.getMessage(MESSAGE_CODE_EDIT_HOME_SERVICE)
        );
    }



    @Override
    public DeleteHomeServiceResponseDTO deleteHomeServiceRequest(String requestId, DeleteHomeServiceRequestDTO deleteHomeServiceRequestDTO) {
        var service = homeServiceDAO.findHomeService(deleteHomeServiceRequestDTO.getServiceId());
        homeServiceDAO.deleteHomeService(service.getServiceId());
        return HomeServiceMapper.mapTODeleteHomeServiceResponseDTO(messageSourceUtil
                .getMessage(MESSAGE_CODE_DELETE_HOME_SERVICE));
    }
}
