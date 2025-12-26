package com.shyam.service.Imp;


import com.shyam.validation.RowValidationError;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.shyam.common.exception.domain.SYMErrorType;
import com.shyam.common.exception.domain.SYMException;
import com.shyam.common.util.MessageSourceUtil;
import com.shyam.constants.ErrorCodeConstants;
import com.shyam.dao.CategoryDAO;
import com.shyam.dto.request.AddCategoryRequestDTO;
import com.shyam.dto.request.GetCategoryByIdRequestDTO;
import com.shyam.dto.response.AddCategoryResponseDTO;
import com.shyam.dto.response.GetCategoryByIdResponseDTO;
import com.shyam.dto.response.GetCategoryResponseDTO;
import com.shyam.dto.response.UpdateCategoryResponseDTO;
import com.shyam.mapper.CategoryMapper;
import com.shyam.service.CategoryService;
import com.shyam.validation.CategoryExcelValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static com.shyam.constants.MessageConstant.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final MessageSourceUtil messageSourceUtil;
    private final CategoryDAO categoryDAO;
    private final CategoryExcelValidation categoryExcelValidation;

    @Override
    public GetCategoryResponseDTO getAllCategories() {
        log.info("Processing the request for get category");
        return categoryMapper.getAllCategories();
    }

    @Override
    public AddCategoryResponseDTO addCategories(AddCategoryRequestDTO addCategoryRequestDTO ) {
        log.info("Processing the request for adding category");
        if (!categoryDAO.isNameAvailable(addCategoryRequestDTO.getName())) {
            throw new SYMException(HttpStatus.BAD_REQUEST,
                    SYMErrorType.VALIDATION_FAILED,
                    ErrorCodeConstants.ERROR_CODE_VALIDATION,
                    "Category name already exists.",
                    "Duplicate category name");
        }
        var newCategory =CategoryMapper.addCategories(addCategoryRequestDTO);
        categoryDAO.saveCategory(newCategory);
        return categoryMapper.mapToAddCategoryInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_ADD_CATEGORY));
    }

    @Override
    public UpdateCategoryResponseDTO updateCategoryRequestDTO(AddCategoryRequestDTO updateCategoryRequestDTO ) {
        log.info("Processing the request for updating category");
        categoryMapper.updateCategoryRequestDTO(updateCategoryRequestDTO);
        return categoryMapper.mapToUpdateCategoryInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_UPDATE_CATEGORY));
    }

    @Override
    public GetCategoryByIdResponseDTO getCategory(GetCategoryByIdRequestDTO getCategoryByIdRequestDTO) {
        log.info("Received request for getting category By Id ");
        var category  = categoryDAO.findById(getCategoryByIdRequestDTO.getId());
         return CategoryMapper.getCategory(category);
    }

    @Override
    public UpdateCategoryResponseDTO deleteCategory(GetCategoryByIdRequestDTO updateCategoryRequestDTO ) {
        log.info("Received request for deleting category By Id ");
        var category  = categoryDAO.findById(updateCategoryRequestDTO.getId());
        categoryDAO.deleteCategory(updateCategoryRequestDTO.getId());
        return CategoryMapper.mapToDeleteCategoryInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_DELETE_CATEGORY));
    }

    @Override
    public ResponseEntity<?> uploadExcel(MultipartFile file, String createdBy) {
        log.info("Processing the request for upload excel sheet");
        try {
            try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

                Sheet sheet = workbook.getSheetAt(0);

                // Validation
                List<RowValidationError> errors = categoryExcelValidation.validateExcel(sheet);

                if (!errors.isEmpty()) {
                    byte[] errorFile = generateErrorExcel(errors);

                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Content-Disposition", "attachment; filename=ErrorReport.xlsx");
                    headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

                    return ResponseEntity
                            .badRequest()
                            .headers(headers)
                            .body(errorFile);
                }


                // Save data if no errors
                DataFormatter formatter = new DataFormatter();
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    String name = formatter.formatCellValue(row.getCell(0)).trim();
                    String statusStr = formatter.formatCellValue(row.getCell(1)).trim();
                    boolean status = "active".equalsIgnoreCase(statusStr) ||
                            "true".equalsIgnoreCase(statusStr) ||
                            "1".equals(statusStr);

                    var category = CategoryMapper.uploadExcel(name, status, createdBy);
                    categoryDAO.saveCategory(category);
                }
            }

        } catch (SYMException e) {
            throw e;
        } catch (IOException e) {
            throw new SYMException(HttpStatus.INTERNAL_SERVER_ERROR, SYMErrorType.GENERIC_EXCEPTION,
                    ErrorCodeConstants.ERROR_CODE_AUTHZ_UNKNOWN, "Failed to read Excel file", e.getMessage());
        } catch (Exception e) {
            throw new SYMException(HttpStatus.INTERNAL_SERVER_ERROR, SYMErrorType.GENERIC_EXCEPTION,
                    ErrorCodeConstants.ERROR_CODE_AUTHZ_UNKNOWN, "Failed to upload categories from Excel", e.getMessage());
        }

        AddCategoryResponseDTO response = categoryMapper.mapToAddCategoryInMessage(
                messageSourceUtil.getMessage(MESSAGE_CODE_ADD_CATEGORY)
        );
        return ResponseEntity.ok(response);
    }

    private byte[] generateErrorExcel(List<RowValidationError> errors) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Validation_Errors");

        // Header
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Name");
        header.createCell(1).setCellValue("Status");
        header.createCell(2).setCellValue("Reason");

        // Data
        int rowNum = 1;
        for (RowValidationError error : errors) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(error.getName());
            row.createCell(1).setCellValue(error.getStatus());
            row.createCell(2).setCellValue(error.getReason());
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();
        return bos.toByteArray();
    }


}
