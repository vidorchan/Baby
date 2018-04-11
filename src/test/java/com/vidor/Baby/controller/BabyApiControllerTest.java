package com.vidor.Baby.controller;

import com.vidor.Baby.repository.BabyRepository;
import com.vidor.Baby.service.BabyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BabyApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private BabyService babyService;

    @Mock
    private BabyRepository babyRepository;

    @Test
    public void getAll() throws Exception {
        this.mvc.perform(get("/babies/").accept(MediaType.ALL)).andExpect(status().isOk());
    }

    @Test
    public void addBaby() {
    }

    @Test
    public void updateBaby() {
    }

    @Test
    public void deleteBaby() {
    }

    @Test
    public void findBaby() {
    }

    @Test
    public void findBabiesByAge() throws Exception {
        when(babyRepository.findByAge(anyInt())).thenReturn(null);
        this.mvc.perform(get("/babies/age/1").param("age","1"))
                .andExpect(status().isOk());
        verify(this.babyRepository,never()).findByAge(anyInt());
    }

    @Test
    public void insert() throws Exception {
//        Mockito.doAnswer(new Answer() {
//            public Object answer(InvocationOnMock invocation) {
//                Object[] args = invocation.getArguments();
//                return null;
//            }
//        }).when(babyService).insertTwo();
        Mockito.doNothing().when(Mockito.spy(babyService)).insertTwo();
        this.mvc.perform(post("/babies/insertTwo").accept(MediaType.ALL))
                .andExpect(content().string("insert success"));
        verify(babyService,never()).insertTwo();
    }
}