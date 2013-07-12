package com.netshelter.ifbrands.etl.dataplatform.model;

public class GetInfluenceRollupBreakdownResponse
{
  private DpInfluenceRollup organic, paid, total;

  public DpInfluenceRollup getOrganic()
  {
    return organic;
  }

  public void setOrganic( DpInfluenceRollup organic )
  {
    this.organic = organic;
  }

  public DpInfluenceRollup getPaid()
  {
    return paid;
  }

  public void setPaid( DpInfluenceRollup paid )
  {
    this.paid = paid;
  }

  public DpInfluenceRollup getTotal()
  {
    return total;
  }

  public void setTotal( DpInfluenceRollup total )
  {
    this.total = total;
  }

}
