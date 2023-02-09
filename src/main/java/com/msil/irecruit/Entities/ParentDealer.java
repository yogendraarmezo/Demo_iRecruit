package com.msil.irecruit.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="parentDealer")
public class ParentDealer {
	 @Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
		private Integer id;
	    private String parentDealerName;
	    private String parentDealerCode;
	    private Boolean status=true;
	    
		public ParentDealer() {
		}

		

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}



		public String getParentDealerName() {
			return parentDealerName;
		}

		public void setParentDealerName(String parentDealerName) {
			this.parentDealerName = parentDealerName;
		}

		public String getParentDealerCode() {
			return parentDealerCode;
		}

		public void setParentDealerCode(String parentDealerCode) {
			this.parentDealerCode = parentDealerCode;
		}

		public Boolean getStatus() {
			return status;
		}

		public void setStatus(Boolean status) {
			this.status = status;
		}
		
}
