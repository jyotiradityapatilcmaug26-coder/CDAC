#include <iostream>
#include <algorithm>
using namespace std;

int EMP_ID = 1001;

class Employee{
private:
	int empId;
	string name;
	string department;
	char grade;
	double basicSalary;
	bool isActive = true;
public:
	//adding inline or see line before main
	inline static int employeeCount;
	Employee(){
		empId = EMP_ID++;
//		cout<<empId<<endl;
		employeeCount++;
//		cout<<employeeCount<<endl;
	}

	//Setters
	void setName(const string& n){
		if(!n.size()){
			cout<<"Name cannot be null"<<endl;
			return;
		}
		name = n;
	}
	void setDepartment(const string& dept){
		if(dept == "Engineering" || dept == "HR" || dept == "Finance" || dept == "Operations"){
			department = dept;
		}
		else{
			cout<<"Salary must be between Rs.10,000 and Rs.5,00,000. Value rejected."<<endl;
			return;
		}

	}
	void setGrade(char g){
		if(g == 'A' || g == 'B' || g == 'C' || g == 'D'){
			grade = g;
		}
		else{
			cout<<"ERROR: Invalid grade 'X'. Accepted values: A, B, C, D."<<endl;
			return;
		}
	}
	void setBasicSalary(double salary){
		if(salary < 10000 || salary > 500000){
			cout<<"Salary must be between Rs.10,000 and Rs.5,00,000. Value rejected."<<endl;
			return;
		}
		basicSalary = salary;
	}
	void deactivate(){
		isActive = false;
		employeeCount--;
	}

	int getEmpId() const{
		return empId;
	}
	string getName() const{
		return name;
	}
	string getDepartment() const{
		return department;
	}
	char getGrade() const{
		return grade;
	}
	double getBasicSalary() const{
		return basicSalary;
	}
	bool getIsActive() const{
		return isActive;
	}


	void acceptDetails(){
		string t;
		cout<<"Enter Name: "<<endl;
		getline(cin, t);
		setName(t);

		cout<<endl;
		cout<<"Enter Department: "<<endl;
		getline(cin, t);
		setDepartment(t);

		cout<<endl;
		cout<<"Enter Grade: "<<endl;
		getline(cin, t);
		setGrade(t[0]);

		cout<<endl;
		cout<<"Enter Basic Salary: "<<endl;
		getline(cin, t);
		setBasicSalary(stod(t));
	}

	static int getEmployeeCount(){
		return employeeCount;
	}

	double computeAllowances() const{
		// Grade A → 40% of basicSalary (Senior / Manager)
		// Grade B → 30% of basicSalary (Mid-level)
		// Grade C → 20% of basicSalary (Junior)
		// Grade D → 10% of basicSalary (Trainee)
//		cout<<"Compute Allowance grade : "<<grade<<endl;
		double ret;
		if(grade == 'A'){
			ret = (double)(basicSalary * 0.4);
		}
		else if(grade == 'B'){
			ret = (double)(basicSalary * 0.3);
		}
		else if(grade == 'C'){
			ret = (double)(basicSalary * 0.2);
		}
		else{
			ret = (double)(basicSalary * 0.1);
		}

		return ret;
	}

	double computeGrossSalary() const{
//		cout<<"Gross Salary: "<<basicSalary + computeAllowances()<<endl;
		return basicSalary + computeAllowances();

	}

	double computeTax() const{
		// Gross <= 50,000 → 0%
		// 50,001 to 1,00,000 → 10% on amount above 50,000
		// > 1,00,000 → 5,000 + 20% on amount above 1,00,000

		double grossSalary = computeGrossSalary();
		if(grossSalary <= 50000) return 0;
		else if(grossSalary >= 50001 && grossSalary <= 100000) return (double)((grossSalary-50000)*0.1);
		else if(grossSalary >= 100001) return (double)(5000 + (grossSalary*0.2));

		return 0.0;
	}

	double computeNetSalary() const{
		return (double)(computeGrossSalary() - computeTax());
	}


	void printPayslip() const{
		cout<<"============================================"<<endl;
		cout<<"EMPLOYEE PAYSLIP — AUG 2026" <<endl;
		cout<<"============================================"<<endl;
		cout<<"EMP ID: "<<empId<<endl;
		cout<<"Name: "<<name<<endl;
		cout<<"Department: "<<department<<endl;
		cout<<"Grade: "<<grade<<endl;
		cout<<"Status: "<<((isActive) ? "Active" : "Inactive")<<endl;
		cout<<"--------------------------------------------"<<endl;
		cout<<"Basic Salary: "<<basicSalary<<endl;
		cout<<"Allowances (30%): "<<computeAllowances()<<endl;
		cout<<"Gross Salary: "<<computeGrossSalary()<<endl;
		cout<<"--------------------------------------------"<<endl;
		cout<<"Tax Deduction: "<<computeTax()<<endl;
		cout<<"Net Salary: "<<computeNetSalary()<<endl;
		cout<<"--------------------------------------------\n";

	}

};
//Priya Sharma Engineering B 75000
//this is the way you define static variables inside the class in CPP, because you need to allocate memory for static variable
//for this particular .cpp file, so using the below syntax or using inline, you tell the compiler this static variable belongs
//to this cpp file

//int Employee::employeeCount = 0;

int main() {
	// Create objects — one on stack, two on heap
	 Employee e1;
	 Employee* e2 = new Employee();
	 Employee* e3 = new Employee();
	 e1.acceptDetails();
	 e2->acceptDetails();
	 e3->acceptDetails();
	 // Try uncommenting — observe the compiler error, then explain in a commentwhy
	 //both of them are private varibles inside our class, private variables cannot be access outside it's own class
	 //they can be accessed using getter setter functions inside them.
//	 e1.empId = 999;
//	 e1.basicSalary = -1000;
	 e1.printPayslip();
	 e2->printPayslip();
	 e3->printPayslip();
	 // Simulate a resignation
	 e3->deactivate();
	 if (!e3->getIsActive())
	 cout << e3->getName() << " is no longer active. Payroll skipped." << endl;
	 cout << "Total Employees : " << Employee::getEmployeeCount() << endl;
	 delete e2;
	 delete e3;
	 return 0;

	return 0;
}