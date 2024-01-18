package com.booking.smartx.dto.requestDto;

import com.booking.smartx.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
public class RegisterMerchantDto {
    private String businessName;
    private String email;
    private String phoneNumber;
    private String password;
    private String postCode;
    private String address;
    private String businessType;
    private int numberOfEmployees;
    private String role;
    private int token;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        if(!(Utils.validateIntegerInput(numberOfEmployees) <= 0)){
            this.numberOfEmployees = numberOfEmployees;
        }else{
            this.numberOfEmployees = 1;
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "registerMerchantDto{" +
                "businessName='" + businessName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", postCode='" + postCode + '\'' +
                ", address='" + address + '\'' +
                ", businessType='" + businessType + '\'' +
                ", numberOfEmployees=" + numberOfEmployees +
                ", token=" + token +
                '}';
    }
}
