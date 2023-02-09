package com.msil.irecruit.Repositories;



import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.State;



public interface OutletRepository extends JpaRepository<Outlet,Integer>{

	String outletQuery="SELECT o FROM Outlet o WHERE o.outletCode = :outletCode";
	@Query(outletQuery)
	Outlet getOutletByCode(@Param("outletCode") String outletCode);
	

	 @Query("SELECT o FROM Outlet o WHERE o.Region.id = :regionId")
	 List<Outlet> getOutletByRgion(@Param("regionId") Integer region_regionId);
	 
	 @Query("SELECT DISTINCT(o.State) FROM Outlet o WHERE o.Region.id IN :regions")
	 List<State> getOutletByRgionList(List<Integer> regions);
	
	 @Query("SELECT out FROM Outlet out WHERE out.outletCode=:outletName AND out.dealer.id=:dealerId")
	 Optional<Outlet> findByNameAndDealerId(@Param("outletName") String outletName,@Param("dealerId")  Long dealer_id);
	 
	 @Query("SELECT out FROM Outlet out WHERE out.outletCode=:outletCode AND out.dealer.id=:dealerId")
	 Optional<Outlet> findByOutletCodeAndDealerId(@Param("outletCode") String outletCode,@Param("dealerId")  Long dealer_id);
	 
	 @Query("SELECT o FROM Outlet o WHERE o.dealer.id = :dealerId")
	Optional<List<Outlet>> findByDealerId(@Param("dealerId") Long dealerId);
	 
	 @Query("SELECT out FROM Outlet out WHERE out.outletName=:outletName AND out.dealer.id=:dealerId")
	Optional<Outlet> findByNameAndDealerId1(@Param("outletName") String outletName,@Param("dealerId")  Long dealer_id);
	 @Query("SELECT o FROM Outlet o WHERE o.dealer.id =:dealerId")
		List<Outlet> findOutletByDealerId(@Param("dealerId") Long dealerId);


	 @Query("SELECT o FROM Outlet o WHERE o.City.cityCode IN :citiCodes")
	List<Outlet> findOutletByCityCodes(List<String> citiCodes);


	 @Query("SELECT o.parentDealer.id FROM Outlet o WHERE o.City.cityCode IN :citiCodes")
	List<Integer> findParentDealerIdByCityCodes(List<String> citiCodes);


	 @Query("SELECT o.outletId FROM Outlet o WHERE o.parentDealer.parentDealerCode IN :pdCodeList")
	List<Integer> findDealerIdByPDCodes(List<String> pdCodeList);


	 @Query("SELECT o.dealer.id FROM Outlet o WHERE o.outletId IN :outletIds")
	List<Integer> findDealerIdsByOutletId(List<Integer> outletIds);

	 @Query("SELECT o FROM Outlet o WHERE  (o.parentDealer.parentDealerCode IN :pdList OR (:pdList) IS NULL) AND"
	 		+ " (o.dealer.mspin IN :dList OR (:dList) IS NULL) AND (o.Region.regionCode IN :rgList OR (:rgList) IS NULL)")
	 List<Outlet> findAllOutletByPDCodeDealerMspinRegionId(List<String> pdList, List<String> dList,List<String> rgList );
	 
	 @Query("SELECT o FROM Outlet o WHERE  (o.parentDealer.parentDealerCode IN :pdList OR (:pdList) IS NULL) and"
		 		+ "  (o.Region.regionCode IN :rgList OR (:rgList) IS NULL) and (o.dealer.id IN :dList OR (:dList) IS NULL) and"
		 		+ "(o.State.stateCode IN :stateList OR (:stateList) IS NULL) and (o.City.cityCode IN :cityList OR (:cityList) IS NULL)"
		 		+ " and (o.Region.fsdm.id IN :fList OR (:fList) IS NULL)")
		 List<Outlet> getDealerForHO(List<String> pdList, List<Long> dList,List<String> rgList,List<String> cityList,List<String> stateList,List<Integer> fList );

	 @Query("SELECT o FROM Outlet o WHERE o.parentDealer.parentDealerCode IN :pdCodeList")
	List<Outlet> getOutletsByPDCodes(List<String> pdCodeList);


	 @Query("SELECT o FROM Outlet o WHERE (o.Region.regionCode IN (:rgList1) OR (:rgList1) IS NULL) AND (o.State.stateCode IN (:stList) OR (:stList) IS NULL) AND (o.City.cityCode IN (:ctList) OR (:ctList) IS NULL) AND (o.parentDealer.parentDealerCode IN (:pdList) OR (:pdList) IS NULL) AND (o.Region.fsdm.id IN (:fsdmList) OR (:fsdmList) IS NULL) AND (o.dealer.id IN (:dList) OR (:dList) IS NULL)")
	List<Outlet> getDealerForHOFilter(List<String> rgList1, List<String> stList, List<String> ctList,
			List<String> pdList, List<Integer> fsdmList, List<Long> dList);


	 @Query("SELECT o FROM Outlet o WHERE o.Region.fsdm.id =:i")
	List<Outlet> getAllOutletByFSDMId(Integer i);

	@Query("SELECT o FROM Outlet o WHERE (o.Region.id =:region OR :region IS NULL OR :region='') AND (o.State.stateCode=:stateCode OR :stateCode IS NULL OR :stateCode='') AND (o.City.cityCode=:cityCode OR :cityCode IS NULL OR :cityCode='') AND "
			+ "(o.outletCode=:dealership OR :dealership IS NULL OR :dealership='') AND (o.outletCode=:outletCode OR :outletCode IS NULL OR :outletCode='') AND (o.dealer.id=:dealerId OR :dealerId IS NULL OR :dealerId='')")
	List<Outlet> getOutletForDashboardFilterReport(Integer region, String stateCode, String cityCode, String dealership,
			String outletCode, Long dealerId);


	@Query("SELECT o FROM Outlet o WHERE o.State.stateCode=:stateCode")
	List<Outlet> getOutletByStateCode(String stateCode);

	@Query("SELECT o FROM Outlet o WHERE o.Region.regionCode=:regionCode")
	List<Outlet> getOutletByRegionCode(String regionCode);

	@Query("SELECT o FROM Outlet o WHERE o.outletCode = :outletCode")
	Optional<Outlet> getOutletByOutletCode(String outletCode);
	 
	 
	 
	 @Query("SELECT o FROM Outlet o WHERE  "
		 		+ "(o.Region.regionCode =:regionCode OR :regionCode IS NULL) and "
		 		+ "(o.State.stateCode =:stateList OR :stateList IS NULL) and "
		 		+ "(o.City.cityCode =:cityList OR :cityList IS NULL) and "
		 		+ "(o.outletCode =:outletCode OR :outletCode IS NULL)")
		 List<Outlet> getOutletByRegoinStateCityDealerOutlet(String regionCode,String cityList,String stateList,String outletCode );
	 
	 @Query("SELECT o FROM Outlet o WHERE (o.Region.regionCode =:regionCode OR :regionCode IS NULL OR :regionCode ='') AND (o.State.stateCode =:stateCode OR :stateCode IS NULL or :stateCode ='') AND (o.City.cityCode =:cityCode OR :cityCode IS NULL OR :cityCode='') AND (o.parentDealer.parentDealerCode =:parentDealerCode OR :parentDealerCode IS NULL OR :parentDealerCode='') AND (o.Region.fsdm.id =:fId OR :fId IS NULL) AND (o.dealer.id =:dId OR :dId IS NULL)")
	    List<Outlet> getOutletForHoFilter(final String regionCode, final String stateCode, final String cityCode, final String parentDealerCode, final Integer fId, final Long dId);
	
	    @Modifying
	    @Query("update Outlet  set location =:location   where outletId =:outletId")
	    public int updateOutlet(Integer outletId,String location);
}
