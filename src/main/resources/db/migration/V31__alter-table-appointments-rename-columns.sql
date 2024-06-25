alter table appointments rename column paciente to patient;
alter table appointments rename column medico to medic;
alter table appointments rename column data to date;
alter table appointments rename column horario to time;
alter table appointments rename column criacao to created_at;
alter table appointments rename column modificacao to modified_at;
