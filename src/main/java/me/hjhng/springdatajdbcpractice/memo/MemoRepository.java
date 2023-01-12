package me.hjhng.springdatajdbcpractice.memo;

import org.springframework.data.repository.CrudRepository;

public interface MemoRepository extends CrudRepository<Memo, Long> {}
