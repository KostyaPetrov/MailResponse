package ru.konstantinpetrov.mailresponse.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.konstantinpetrov.mailresponse.backend.entity.Mark;
import ru.konstantinpetrov.mailresponse.backend.repository.MarkRepository;

@Service
public class MarkServiceImpl implements MarkService {

    private MarkRepository markRepository;

    @Autowired
    public void setMarkRepository(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    @Override
    public void addMark(Mark mark){
        try{
            this.markRepository.save(mark);
        }catch(Exception exception){
            throw new IllegalArgumentException("Client with current login is already exists!");
        }   
    }
}
