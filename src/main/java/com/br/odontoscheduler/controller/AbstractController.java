package com.br.odontoscheduler.controller;

import com.br.odontoscheduler.dto.BaseEntity;
import com.br.odontoscheduler.dto.BaseResponse;
import com.br.odontoscheduler.model.User;
import com.br.odontoscheduler.service.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public abstract class AbstractController<S extends BaseEntity, T extends AbstractService> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public abstract T getService();

    @GetMapping
    public ResponseEntity<?> getAll(@AuthenticationPrincipal User user){
        try{
            logger.info(user.getName());
            List<S> objectList= this.getService().findAll();
            return new ResponseEntity<>(objectList, HttpStatus.OK);
        } catch (Exception e){
            logger.info("Erro getting all " + e);
            BaseResponse baseResponse = new BaseResponse(BaseResponse.MESSAGE_ERROR,
                    HttpStatus.BAD_REQUEST.toString());
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@AuthenticationPrincipal User user, @PathVariable String id){
        BaseResponse baseResponse = null;
        try{
            logger.info("getting " + id);
            Optional<S> optionalObj = this.getService().findById(id);
            if(optionalObj.isPresent()){
                return new ResponseEntity<>(optionalObj.get(), HttpStatus.OK);
            }
            baseResponse = new BaseResponse(BaseResponse.MESSAGE_NOT_FOUND + id, HttpStatus.NOT_FOUND.toString());
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.info("Erro getting " + id + " " + e);
            baseResponse = new BaseResponse(BaseResponse.MESSAGE_ERROR + id,
                    HttpStatus.BAD_REQUEST.toString());
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@AuthenticationPrincipal User user, @RequestBody S object){
        try{
            logger.info("saving " + object.toString());
            object.setCreatedAt(LocalDateTime.now());
            return new ResponseEntity<>(this.getService().save(object), HttpStatus.CREATED);
        } catch (Exception e){
            logger.info("Erro saving " + object.toString() + " " + e);
            BaseResponse baseResponse = new BaseResponse(BaseResponse.MESSAGE_ERROR,
                    HttpStatus.BAD_REQUEST.toString());
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user, @PathVariable String id){
        BaseResponse baseResponse = null;
        try{
            logger.info("deleting " + id);
            this.getService().delete(id);
            baseResponse = new BaseResponse(BaseResponse.MESSAGE_SUCCESS + id, HttpStatus.OK.toString());
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (Exception e){
            logger.info("Erro deleting " + id + " " + e);
            baseResponse = new BaseResponse(BaseResponse.MESSAGE_ERROR + id, HttpStatus.BAD_REQUEST.toString());
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
