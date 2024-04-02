
package com.example.zboruri.Repository.Paging;
import com.example.zboruri.Domain.Entity;
import com.example.zboruri.Repository.Repository;

public interface IPagingRepository<ID , E extends Entity<ID>> extends Repository<ID, E> {

    Page<E> findAll(Pageable pageable);
}
