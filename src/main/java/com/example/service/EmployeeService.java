package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;

/**
 * 従業員情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	/**1ページあたりの表示件数**/
	private static Integer PAGING_LIMIT = 10;

	/**
	 * 従業員情報を全件取得します.
	 * 
	 * @return 従業員情報一覧
	 */
	public List<Employee> showList() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList;
	}

	public List<Employee> searchByNameLike(String name) {
		return employeeRepository.findByNameLike(name);
	}

	public List<Employee> fetchEmployeeListPaging(Integer p, String name){
		int offset = (p-1)*PAGING_LIMIT;
		return employeeRepository.findByNameLikeWithPagination(name, PAGING_LIMIT, offset);
	}

	public int getTotalPages(String name){
		List<Employee> employees = employeeRepository.findByNameLikeWithPagination(name, null, null);
		return (employees.size()/PAGING_LIMIT) + (employees.size()%PAGING_LIMIT == 0 ? 0 : 1);
	}

	/**
	 * 従業員情報を取得します.
	 * 
	 * @param id ID
	 * @return 従業員情報
	 * @throws org.springframework.dao.DataAccessException 検索されない場合は例外が発生します
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}

	/**
	 * 従業員情報を更新します.
	 * 
	 * @param employee 更新した従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}
}
