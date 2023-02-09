package com.msil.irecruit.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="designation_urban_rural")
public class DesignationUrbanRural {
	 @Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
		private Integer id;
		private String designationName;
		private String designationCode;
		private String category; 
		private String RecruitmentProfile;
		private Boolean status;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getDesignationName() {
			return designationName;
		}
		public void setDesignationName(String designationName) {
			this.designationName = designationName;
		}
		public String getDesignationCode() {
			return designationCode;
		}
		public void setDesignationCode(String designationCode) {
			this.designationCode = designationCode;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getRecruitmentProfile() {
			return RecruitmentProfile;
		}
		public void setRecruitmentProfile(String recruitmentProfile) {
			RecruitmentProfile = recruitmentProfile;
		}
		public Boolean getStatus() {
			return status;
		}
		public void setStatus(Boolean status) {
			this.status = status;
		}
		
		
}
