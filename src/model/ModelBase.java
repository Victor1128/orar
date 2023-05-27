package model;

public abstract class ModelBase {
  private Long id;

  public ModelBase(){};

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
