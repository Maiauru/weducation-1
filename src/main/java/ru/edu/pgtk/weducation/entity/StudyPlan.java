package ru.edu.pgtk.weducation.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Учебный план.
 *
 * @author Воронин Леонид
 */
@Entity
@Table(name = "plans")
public class StudyPlan implements Serializable {

  @Id
  @Column(name = "pln_pcode")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "pln_name", nullable = false, length = 255)
  private String name;

  @Column(name = "pln_description", length = 255)
  private String description;

  @ManyToOne
  @JoinColumn(name = "pln_spccode", nullable = false)
  private Speciality speciality;
  
  @ManyToOne
  @JoinColumn(name = "pln_sfmcode", nullable = false)
  private StudyForm studyForm;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Speciality getSpeciality() {
    return speciality;
  }

  public void setSpeciality(Speciality speciality) {
    this.speciality = speciality;
  }
}