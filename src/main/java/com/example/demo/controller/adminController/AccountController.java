package com.example.demo.controller.adminController;

import com.example.demo.DTO.AccountDTO;
import com.example.demo.DTO.AccountResponse;
import com.example.demo.DTO.Password;
import com.example.demo.DTO.RegisterRequest;
import com.example.demo.exception.MailException;
import com.example.demo.exception.UsernameExitException;
import com.example.demo.model.Account;
import com.example.demo.model.utils.CheckEnabled;
import com.example.demo.model.utils.PagingHeaders;
import com.example.demo.model.utils.PagingResponse;
import com.example.demo.service.AccountService;
import com.example.demo.util.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/acc")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/getAcc")
    public void getAllUser() {

    }

    @GetMapping("/page")
    public AccountResponse getAllUsers(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return accountService.getAllUsersPage(pageNo, pageSize, sortBy, sortDir);
    }

    @PostMapping("/createAcc/{roleId}")
    public ResponseEntity<String> createNewAccount(@RequestBody @Valid RegisterRequest registerRequest, @PathVariable int roleId, BindingResult bindingResult) throws UsernameExitException, MailException {
        if (!bindingResult.hasErrors()){
            return new ResponseEntity<>(accountService.createAccount(registerRequest, roleId), HttpStatus.OK);
        }else {
            throw new RuntimeException("Not valid");
        }
    }

    @PostMapping("/getListAccEnabled")
    public ResponseEntity<List<AccountDTO>> getAccEnabled(@RequestBody CheckEnabled check) {
        return new ResponseEntity<>(accountService.getAccountByEnabled(check.isCheckEnabled()), HttpStatus.OK);
    }
    @PostMapping("/checkPassword/{username}")
    public ResponseEntity<Boolean> checkPassword(@RequestBody Password password, @PathVariable String username){
        Account account = accountService.getAccountByUsername(username);
        return new ResponseEntity<>(accountService.checkPasswordForAccount(account, password.getPassword()), HttpStatus.OK);
    }

    @PostMapping("/changePassword/{username}")
    public ResponseEntity<String> changePassword(@RequestBody Password password, @PathVariable String username){
        Account account = accountService.getAccountByUsername(username);
        return new ResponseEntity<>(accountService.changePasswordForAccount(account, password.getPassword()), HttpStatus.OK);
    }
    public HttpHeaders returnHttpHeaders(PagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }
}
