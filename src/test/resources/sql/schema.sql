CREATE TABLE Memo
(
    memoId  bigint generated by default as identity,
    title   varchar(255)  not null,
    content varchar(2000) null,
    primary key (memoId)
);