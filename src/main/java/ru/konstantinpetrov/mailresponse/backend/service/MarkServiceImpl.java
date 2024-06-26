package ru.konstantinpetrov.mailresponse.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public void addMark(Mark mark){
        try{
            this.markRepository.save(mark);
        }catch(Exception exception){
            System.out.println("Error: " + exception);
            exception.printStackTrace();
        }   
    }
}
