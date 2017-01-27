package in.doubloons.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;




import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.doubloons.dao.BirdsDao;
import in.doubloons.dto.BirdsDto;
import in.doubloons.dto.BirdsResponse;

@Repository
public class BirdsDaoImpl implements BirdsDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	
		 public BirdsDaoImpl() {
			// TODO Auto-generated constructor stub
		}
		
		public BirdsDaoImpl(SessionFactory sessionFactory) {
			super();
			this.sessionFactory = sessionFactory;
		}
		
	    @SuppressWarnings("unchecked")
		@Transactional
	public List<BirdsDto> listAllBirds() {
	    	Session session= sessionFactory.openSession();
	    	List<BirdsDto> birds = new ArrayList<BirdsDto>();
	    	try{
	    		birds= session.createQuery("from BirdsDto where visible = :visiblity")
	    			.setParameter("visiblity", true).list();
	    	}
	    	catch(HibernateException ex){
	    		System.out.println(ex.getCause());
	    	}
	    	catch(ArrayIndexOutOfBoundsException ex){
	    		System.out.println(ex.getCause());
	    	}
	    	
	    	
		return birds;
	}
	    
	    public BirdsDto getBirdById(String birdId) {
	    	Session session= sessionFactory.openSession();
	    	BirdsDto bird = new BirdsDto();
	    	
	    	try{
	    		bird= (BirdsDto) session.createQuery("from BirdsDto where visible = :visiblity and id =:birdId")
		    			.setParameter("visiblity", true)
		    			.setParameter("birdId", birdId).list().get(0);
	    	}
	    	catch(HibernateException ex){
	    		System.out.println(ex.getCause());
	    	}
	    	catch(ArrayIndexOutOfBoundsException ex){
	    		System.out.println(ex.getCause());
	    	}
	    	
	    	
		return bird;
	}
	    
	    public BirdsResponse deleteBird(String birdId) {
	    	Session session= sessionFactory.openSession();
	    	String msg ="";
	    	BirdsDto bird = new BirdsDto();
	    	BirdsResponse birdresponse = new BirdsResponse();
	    	try{
	    		bird= (BirdsDto) session.createQuery("from BirdsDto where visible = :visiblity and id =:birdId")
		    			.setParameter("visiblity", true)
		    			.setParameter("birdId", birdId).list().get(0);
	    	}
	    	catch(HibernateException ex){
	    		System.out.println(ex.getCause());
	    	}
	    	catch(IndexOutOfBoundsException ex){
	    		System.out.println(ex.getCause());
	    	}
	    	if(bird.getId()==null){
	    		msg="Bird doesnot exist";
	    		birdresponse.setBirdsMessage(msg);
	    		return birdresponse;
	    	}
			 Transaction tx= null;
			 try{
				 tx = session.beginTransaction();
				 session.delete(bird);
				 msg="Bird has  been deleted";
				 tx.commit();
			 }catch (HibernateException e){
				 if(tx!=null){
					 tx.rollback();
					 e.printStackTrace();
					 msg="Bird has not been deleted";
				 }
			 }
			 finally{
				 if(session!=null)
					 session.close();
			 }
			 birdresponse.setBirdsMessage(msg);
			 return birdresponse;
	    }
	
	    public static boolean isValidDate(String input,SimpleDateFormat format){
			try{
				format.parse(input);
					return true;
			}
			catch(ParseException e){
				return false;
			}
	    }
	 public BirdsResponse addBirds(BirdsDto bird){
		 BirdsResponse birdsResponse= new BirdsResponse();
		 String msg="";
		 if(bird.getFamily()==null || bird.getName()==null || bird.getContinents().size()==0){
			 msg="Any of the required feilds i.e. name,family,continents may be missing";
			 birdsResponse.setBirdsMessage(msg);
			 return birdsResponse;
		 }
		 String datePattern = "YYYY-MM-DD";
		 SimpleDateFormat format = new SimpleDateFormat(datePattern);
		 
		 if(bird.getAdded()==null){
			bird.setAdded(format.format(new Date()));
		 }
		 if(!isValidDate(bird.getAdded(), format)){
			 msg="Enter Date in correct format i.e. YYYY-MM-DD";
			 birdsResponse.setBirdsMessage(msg);
			 return birdsResponse;
		 }
		 Session session= sessionFactory.openSession();
		 Transaction tx= null;
		 try{
			 tx = session.beginTransaction();
			 session.save(bird);
			 msg="Bird has been added";
			 tx.commit();
		 }catch (HibernateException e){
			 if(tx!=null){
				 tx.rollback();
				 e.printStackTrace();
				 msg="Bird has not been added";
			 }
		 }
		 finally{
			 if(session!=null)
				 session.close();
		 }
		 birdsResponse.setBirdsMessage(msg);
		 return birdsResponse;
	 }
	 
	 

}
