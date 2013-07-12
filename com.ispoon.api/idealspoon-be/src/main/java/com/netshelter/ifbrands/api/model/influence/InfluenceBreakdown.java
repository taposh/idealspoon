package com.netshelter.ifbrands.api.model.influence;

public class InfluenceBreakdown<T extends InfluenceSummary>
{
  private T organic,paid,total;
  private Lift lift;


  public T getOrganic()
  {
    return organic;
  }

  public void setOrganic( T organic )
  {
    this.organic = organic;
  }

  public T getPaid()
  {
    return paid;
  }

  public void setPaid( T paid )
  {
    this.paid = paid;
  }

  public T getTotal()
  {
    return total;
  }

  public void setTotal( T total )
  {
    this.total = total;
  }

  public Lift getLift()
  {
    return lift;
  }

  public void setLift( Lift lift )
  {
    this.lift = lift;
  }

  public static class Lift
  {
    private double peopleInfluecedLift = 0;
    private double influencersLift = 0;

    public Lift( double peopleInfluecedLift, double influencersLift )
    {
      this.peopleInfluecedLift = peopleInfluecedLift;
      this.influencersLift = influencersLift;
    }

    public double getPeopleInfluecedLift()
    {
      return peopleInfluecedLift;
    }

    public double getInfluencersLift()
    {
      return influencersLift;
    }
  }

}
