package de.hdm.wim.events.documentrepresentation;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import de.hdm.wim.events.model.Company;
import de.hdm.wim.events.model.Document;
import de.hdm.wim.events.model.Employee;
import de.hdm.wim.events.model.Product;
import de.hdm.wim.events.model.Project;
import de.hdm.wim.events.restclient.BaseRestClient;

/**
 * Class used to request further infrmation about a given 'thing',
 * i.e. a project, product, company or employee.
 * Further to request a document regarding given things,
 * i.e. documents in relation to a combination of a project and an employee.
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
public class DocumentRepresentationRequester {

	public static final String DOCUMENT_REPRESENTATION_URL = "http://104.154.103.216/document/rest/";
	
	/**
	 * 
	 * @param searchRequest
	 * @return
	 */
	public DocumentIDsWrapper getDocumentIDs(SearchRequest searchRequest) {
		BaseRestClient restClient = new BaseRestClient(DOCUMENT_REPRESENTATION_URL);
		Response result = restClient.doPostSearchRequest("GetDocumentMetadata", searchRequest); //TODO: use correct path
		DocumentIDsWrapper documentIDsWrapper = result.readEntity(DocumentIDsWrapper.class);
		return documentIDsWrapper;
	}	
	
	/**
	 * 
	 * @return
	 */
	public DocumentTypesWrapper getDocumentTypes() {
		BaseRestClient restClient = new BaseRestClient(DOCUMENT_REPRESENTATION_URL + "GetDocumentClasses/");
		Response response = restClient.doGet("");
		DocumentTypesWrapper documentTypesWrapper = response.readEntity(DocumentTypesWrapper.class);
		return documentTypesWrapper;
	}
	
	/**
	 * 
	 * @param companyID
	 * @return
	 */
	public Company getCompany(String companyID) {
		BaseRestClient restClient = new BaseRestClient(DOCUMENT_REPRESENTATION_URL + "GetCompanyByID/");
		Response response = restClient.doGet(companyID);
		Company company = response.readEntity(Company.class);
		return company;
	}
	/**
	 * 
	 * @param documentID
	 * @return
	 */
	public Document getDocument(String documentID) {
		BaseRestClient restClient = new BaseRestClient(DOCUMENT_REPRESENTATION_URL + "GetDocumentByID/");
		Response response = restClient.doGet(documentID);
		Document document = response.readEntity(Document.class);
		return document;
	}
	
	/**
	 * 
	 * @param projectID
	 * @return
	 */
	public Project getProject(String projectID) {
		BaseRestClient restClient = new BaseRestClient(DOCUMENT_REPRESENTATION_URL + "GetProjectByID/");
		Response response = restClient.doGet(projectID);
		Project project = response.readEntity(Project.class);
		return project;
	}
	
	/**
	 * 
	 * @param employeeID
	 * @return
	 */
	public Employee getEmployee(String employeeID) {
		BaseRestClient restClient = new BaseRestClient(DOCUMENT_REPRESENTATION_URL + "GetEmployeeByID/");
		Response response = restClient.doGet(employeeID);
		Employee employee = response.readEntity(Employee.class);
		return employee;
	}
	
	/**
	 * 
	 * @param productID
	 * @return
	 */
	public Product getProduct(String productID) {
		BaseRestClient restClient = new BaseRestClient(DOCUMENT_REPRESENTATION_URL + "GetProductByID/");
		Response response = restClient.doGet(productID);
		Product product = response.readEntity(Product.class);
		return product;
	}
}