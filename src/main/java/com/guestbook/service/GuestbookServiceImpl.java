package com.guestbook.service;

import com.guestbook.dto.GuestbookDTO;
import com.guestbook.dto.PageRequestDTO;
import com.guestbook.dto.PageResultDTO;
import com.guestbook.entity.Guestbook;
import com.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor    // 의존성 자동 주입
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository;
    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO---------------------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = repository.findAll(pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> optGuestbook = repository.findById(gno);

        return optGuestbook.isPresent() ? entityToDto(optGuestbook.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        Optional<Guestbook> result = repository.findById(dto.getGno());

        if (result.isPresent()) {
            Guestbook guestbook = result.get();

            guestbook.setTitle(dto.getTitle());
            guestbook.setContent(dto.getContent());

            repository.save(guestbook);
        }
    }
}
