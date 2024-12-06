package cat.mood.koryto.service;

import cat.mood.koryto.model.Part;
import cat.mood.koryto.repository.PartDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartService {
    final PartDAO dao;

    public List<Part> getAll() {
        return dao.getAll();
    }
}
