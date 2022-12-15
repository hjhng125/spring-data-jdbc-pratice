package me.hjhng.springdatajdbcpractice;

import org.springframework.data.repository.CrudRepository;

public interface MemoRepository extends CrudRepository<Memo, Long> {}
