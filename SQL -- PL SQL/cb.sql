create table artists
  (
    id   number (7),
    name varchar2 (50 char),
    constraint artists$pk primary key (id),
    constraint artists$name$nn check (name is not null)
  );

create table certifications
  (
    id          number (1),
    name        varchar2 (5 char),
    description varchar2 (250 char),
    constraint cert$pk primary key (id),
    constraint cert$name$nn check (name is not null),
    constraint cert$name$ck check (name in ('G', 'PG', 'PG-13', 'R', 'NC-17')),
    constraint cert$name$un unique (name)
  );

create table companies
  (
    id   number (5),
    name varchar2 (70 char),
    constraint companies$pk primary key (id),
    constraint companies$name_nn check (name is not null)
  );

create table countries
  (
    code char (2 char),
    name varchar2 (40 char),
    constraint countries$pk primary key (code),
    constraint countries$name$nn check (name is not null),
    constraint countries$name$un unique (name)
  );

create table genres
  (
    id   number (5),
    name varchar2 (16 char),
    constraint genres$pk primary key (id),
    constraint genres$name$nn check (name is not null),
    constraint genres$name$un unique (name)
  );

create table languages
  (
    code char (2 char),
    name varchar2 (20 char),
    constraint languages$pk primary key (code),
    constraint languages$name$nn check (name is not null),
    constraint languages$name$un unique (name)
  );

create table logs
  (
    id         number (12),
    when_      date default sysdate,
    error_code integer default 0,
    what_      varchar2 (4000 char),
    where_     varchar2 (255 char),
    constraint logs$pk primary key (id),
    constraint logs$when_$nn check (when_ is not null),
    constraint logs$what_$nn check (what_ is not null),
    constraint logs$error_code$nn check (error_code is not null),
    constraint logs$where_$nn check (where_ is not null)
  );

create table movies
  (
    id                 number (6),
    title              varchar2 (100 char),
    overview           varchar2 (1000 char),
    release_date       date,
    vote_average       number (3,1),
    vote_count         number (6),
    certification      number (4),
    production_country char (2 char),
    runtime            number (3),
    nb_copies          number (3) default 0,
    constraint movies$pk primary key (id),
    constraint movies$title$nn check (title is not null),
    constraint movies$countries$fk foreign key (production_country) references countries(code),
    constraint movies$cert$fk foreign key (certification) references certifications(id),
    constraint movies$runtime$ck check (runtime > 0),
    constraint movies$vote_count$ck check (vote_count >= 0),
    constraint movies$vote_average$ck check (vote_average between 0 and 10),
    constraint movies$nb_copies$nn check (nb_copies is not null),
    constraint movies$nb_copies$ck check (nb_copies >= 0)
  );

create table images
  (
    id number (10),
    image blob default empty_blob(),
    movie number (6),
    constraint images$pk primary key (id),
    constraint images$movie$nn check (movie is not null),
    constraint images$movies$fk foreign key (movie) references movies(id)
  );

create table copies
  (
    num_copy number (6),
    movie    number (6),
    constraint copies$pk primary key (num_copy, movie),
    constraint copies$movies$fk foreign key (movie) references movies(id)
  );

create table production_companies
  (
    company number (5),
    movie   number (6),
    constraint p_c$pk primary key (company, movie),
    constraint p_c$companies$fk foreign key (company) references companies(id),
    constraint p_c$movies$fk foreign key (movie) references movies (id)
  );

create table spoken_lang
  (
    language char (2 char),
    movie    number (6),
    constraint s_l$pk primary key (language, movie),
    constraint s_l$languages$fk foreign key (language) references languages(code),
    constraint s_l$movies$fk foreign key (movie) references movies(id)
  );

create table movie_direct
  (
    movie    number (6),
    director number (7),
    constraint m_d$pk primary key (movie, director),
    constraint m_d$movies$fk foreign key (movie) references movies(id),
    constraint m_d$artists$fk foreign key (director) references artists(id)
  );

create table movie_genres
  (
    genre number (5),
    movie number (6),
    constraint m_g$pk primary key (genre, movie),
    constraint m_g$genres$fk foreign key (genre) references genres(id),
    constraint m_g$movies$fk foreign key (movie) references movies(id)
  ) ;

create table movie_play
  (
    movie  number (6),
    artist number (7),
    constraint m_p$pk primary key (movie, artist),
    constraint m_p$movies$fk foreign key (movie) references movies(id),
    constraint m_p$artists$fk foreign key (artist) references artists(id)
  );
