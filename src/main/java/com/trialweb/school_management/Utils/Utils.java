package com.trialweb.school_management.Utils;

import com.trialweb.school_management.Dtos.*;
import com.trialweb.school_management.Models.*;
import org.apache.tomcat.util.http.fileupload.util.Streams;

import javax.security.auth.Subject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Utils {

    public static StudentDto mapStudentEntityToStudentDto(Students student) {
        StudentDto studentDto = new StudentDto();

        studentDto.setId(student.getId());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setEmail(student.getEmail());
        studentDto.setAge(student.getAge());
        studentDto.setAdm(student.getAdm());

        return studentDto;
    }
    public static StudentDto mapStudentEntityToStudentDtoPlusClassAndStream(Students student, Stream stream) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setEmail(student.getEmail());
        studentDto.setAge(student.getAge());
        studentDto.setAdm(student.getAdm());

        if(student.getClasses()!=null){
            ClassesDto classesDto = mapClassesEntityToClassesDto(student.getClasses());
            studentDto.setClassesDto(classesDto);
        }
        if(stream!=null){
            StreamDto streamDto = mapStreamEntityToStreamDto(stream);
            studentDto.setStreamDto(streamDto);
        }
        return studentDto;
    }
    public static StudentDto mapStudentEntityToStudentDtoPlusDorm(Students student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setEmail(student.getEmail());
        studentDto.setAge(student.getAge());
        studentDto.setAdm(student.getAdm());

        if(student.getDorm()!=null){
            DormDto dormDto = mapDormEntityToDormDto(student.getDorm());
            studentDto.setDormName(dormDto);
        }

        return studentDto;
    }
    public static StudentDto mapStudentEntityToStudentDtoPlusParent(Students student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setEmail(student.getEmail());
        studentDto.setAge(student.getAge());
        studentDto.setAdm(student.getAdm());

        if(student.getParent()!=null){
            ParentDto parentDto = mapParentEntityToParentDto(student.getParent());
            studentDto.setParentName(parentDto);
        }
        return studentDto;
    }
    public static StudentDto mapStudentEntityToStudentDtoPlusSubject(Students student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setEmail(student.getEmail());
        studentDto.setAge(student.getAge());
        studentDto.setAdm(student.getAdm());

        if(student.getSubjects()!=null){
           List<SubjectDto> subjectDto = student.getSubjects().stream()
                    .map(Utils::mapSubjectEntityToSubjectDto)
                    .toList();
           studentDto.setSubjects(subjectDto);
        }
        return studentDto;
    }
    public static StreamDto mapStreamEntityToStreamDto(Stream stream) {
        StreamDto streamDto = new StreamDto();
        streamDto.setId(stream.getId());
        streamDto.setName(stream.getName());
        streamDto.setTotalNumberOfStudents(stream.getTotalNumberOfStudents());

        return  streamDto;
    }
    public static StreamDto mapStreamEntityToStreamDtoPlusStudents(Stream stream) {
        StreamDto streamDto = new StreamDto();
        streamDto.setId(stream.getId());
        streamDto.setName(stream.getName());
        streamDto.setTotalNumberOfStudents(stream.getTotalNumberOfStudents());

        if(stream.getStudents() != null){
            List<StudentDto> studentDto = stream.getStudents().stream()
                    .map(Utils::mapStudentEntityToStudentDto)
                    .toList();
            streamDto.setStudents(studentDto);
        }
        return streamDto;
    }
    public static ClassesDto mapClassesEntityToClassesDto(Classes classes) {
        ClassesDto classesDto = new ClassesDto();
        classesDto.setId(classes.getId());
        classesDto.setName(classes.getName());
        classesDto.setTotalNumberOfStudents(classes.getTotalNumberOfStudents());

        return  classesDto;
    }
    public static ClassesDto mapClassEntityToClassesDtoPlusStudentsAndStream(Classes classes) {
        ClassesDto classesDto = new ClassesDto();
        classesDto.setId(classes.getId());
        classesDto.setName(classes.getName());
        classesDto.setTotalNumberOfStudents(classes.getTotalNumberOfStudents());

        if(classes.getStudents()!=null){
            List<StudentDto> studentDto = classes.getStudents().stream()
                    .map(Utils::mapStudentEntityToStudentDto)
                    .toList();
            classesDto.setStudents(studentDto);
        }
        if(classes.getStream()!=null){
            List<StreamDto> streamDto = classes.getStream().stream()
                    .map(Utils::mapStreamEntityToStreamDto)
                    .toList();
            classesDto.setStream(streamDto);
        }

        return classesDto;
    }
    public static DormDto mapDormEntityToDormDto(Dorm dorm) {
        DormDto dormDto = new DormDto();
        dormDto.setId(dorm.getId());
        dormDto.setName(dorm.getName());
        dormDto.setCapacity(dorm.getCapacity());

        return dormDto;
    }
    public static DormDto mapDormEntityToDormDtoPlusStudents(Dorm dorm){
        DormDto dormDto = new DormDto();
        dormDto.setId(dorm.getId());
        dormDto.setName(dorm.getName());
        dormDto.setCapacity(dorm.getCapacity());

        if(dorm.getStudents()!=null){
            List<StudentDto> studentDto = dorm.getStudents().stream()
                    .map(Utils::mapStudentEntityToStudentDto)
                    .toList();
            dormDto.setStudents(studentDto);
        }
        return dormDto;
    }
    public static ParentDto mapParentEntityToParentDto(Parent parent) {
        ParentDto parentDto = new ParentDto();
        parentDto.setId(parent.getId());
        parentDto.setEmail(parent.getEmail());
        parentDto.setFirstName(parent.getFirstName());
        parentDto.setLastName(parent.getLastName());
        parentDto.setPhoneNumber(parent.getPhoneNumber());
        parentDto.setAddress(parent.getAddress());
        parentDto.setRelationshipWithStudent(parent.getRelationshipWithStudent());

        return parentDto;
    }
    public static ParentDto mapParentEntityToParentDtoPlusRoles(Parent parent) {
        ParentDto parentDto = new ParentDto();
        parentDto.setId(parent.getId());
        parentDto.setEmail(parent.getEmail());
        parentDto.setFirstName(parent.getFirstName());
        parentDto.setLastName(parent.getLastName());
        parentDto.setPhoneNumber(parent.getPhoneNumber());
        parentDto.setAddress(parent.getAddress());
        parentDto.setRelationshipWithStudent(parent.getRelationshipWithStudent());

        if(parent.getRoles()!=null){
            List<RolesDto> rolesDto = parent.getRoles().stream()
                    .map(Utils::mapRoleEntityToRolesDto)
                    .toList();
            parentDto.setRoles(rolesDto);
        }

        return parentDto;
    }
    public static RolesDto mapRoleEntityToRolesDto(Roles role) {
        RolesDto rolesDto = new RolesDto();
        rolesDto.setId(role.getId());
        rolesDto.setRole(role.getRoleName());

        return rolesDto;
    }

    public static SubjectDto mapSubjectEntityToSubjectDto(Subjects subject) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(subject.getId());
        subjectDto.setSubjectName(subject.getSubjectName());
        subjectDto.setSubjectCode(subject.getSubjectCode());

        return subjectDto;
    }
    public static SubjectDto mapSubjectEntityToSubjectDtoPlusStudents(Subjects subject) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(subject.getId());
        subjectDto.setSubjectName(subject.getSubjectName());
        subjectDto.setSubjectCode(subject.getSubjectCode());

        if(subject.getStudents()!=null){
            List<StudentDto> studentDto = subject.getStudents().stream()
                    .map(Utils::mapStudentEntityToStudentDto)
                    .toList();
            subjectDto.setStudents(studentDto);
        }
        return subjectDto;
    }
    public static SubjectDto mapSubjectEntityToSubjectDtoPlusTeachers(Subjects subject) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(subject.getId());
        subjectDto.setSubjectName(subject.getSubjectName());
        subjectDto.setSubjectCode(subject.getSubjectCode());
        if(subject.getTeachers()!=null){
            List<TeacherDto> teacherDto = subject.getTeachers().stream()
                    .map(Utils::mapTeacherEntityToTeacherDto)
                    .toList();
            subjectDto.setTeachers(teacherDto);
        }
        return subjectDto;
    }
    public static SubjectDto mapSubjectEntityToSubjectDtoPlusDepartment(Subjects subject) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(subject.getId());
        subjectDto.setSubjectName(subject.getSubjectName());
        subjectDto.setSubjectCode(subject.getSubjectCode());
        if(subject.getDepartments()!=null){
            DepartmentDto departmentDto = mapDepartmentEntityToDepartmentDto(subject.getDepartments());
            subjectDto.setDepartments(departmentDto);
        }
        return subjectDto;
    }

    public static DepartmentDto mapDepartmentEntityToDepartmentDto(Departments department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setDepartmentName(department.getDepartmentName());

        return departmentDto;
    }
    public static DepartmentDto mapDepartmentEntityToDepartmentDtoPlusTeachers(Departments department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setDepartmentName(department.getDepartmentName());

        if(department.getTeachers()!=null){
            List<TeacherDto> teacherDto = department.getTeachers().stream()
                    .map(Utils::mapTeacherEntityToTeacherDto)
                    .toList();
            departmentDto.setTeachers(teacherDto);
        }
        return departmentDto;
    }
    public static  DepartmentDto mapDepartmentEntityToDepartmentDtoPlusSubjects(Departments department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setDepartmentName(department.getDepartmentName());

        if(department.getSubjects()!=null){
            List<SubjectDto>  subjectDto = department.getSubjects().stream()
                    .map(Utils::mapSubjectEntityToSubjectDto)
                    .toList();
            departmentDto.setSubjects(subjectDto);
        }
        return departmentDto;
    }
    public static TeacherDto mapTeacherEntityToTeacherDto(Teachers teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacher.getId());
        teacherDto.setEmail(teacher.getEmail());
        teacherDto.setFirstName(teacher.getFirstName());
        teacherDto.setLastName(teacher.getLastName());
        teacherDto.setTeacherId(teacher.getTeacherId());
        teacherDto.setPhone(teacher.getPhone());

        return teacherDto;
    }
    public static TeacherDto mapTeacherEntityToTeacherDtoPlusSubject(Teachers teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacher.getId());
        teacherDto.setEmail(teacher.getEmail());
        teacherDto.setFirstName(teacher.getFirstName());
        teacherDto.setLastName(teacher.getLastName());
        teacherDto.setTeacherId(teacher.getTeacherId());
        teacherDto.setPhone(teacher.getPhone());
        if(teacher.getSubjects()!=null){
            List<SubjectDto> subjectDto = teacher.getSubjects().stream()
                    .map(Utils::mapSubjectEntityToSubjectDto)
                    .toList();
            teacherDto.setSubjects(subjectDto);
        }
        return teacherDto;
    }
    public static TeacherDto mapTeacherEntityToTeacherDtoPlusDepartment(Teachers teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacher.getId());
        teacherDto.setEmail(teacher.getEmail());
        teacherDto.setFirstName(teacher.getFirstName());
        teacherDto.setLastName(teacher.getLastName());
        teacherDto.setTeacherId(teacher.getTeacherId());
        teacherDto.setPhone(teacher.getPhone());
        if(teacher.getDepartments()!=null){
            DepartmentDto departmentDto = mapDepartmentEntityToDepartmentDto(teacher.getDepartments());
            teacherDto.setDepartments(departmentDto);
        }
        return teacherDto;
    }
   public static UserDto mapUserEntityToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());

        return userDto;
   }
    public static UserDto mapUserEntityToUserDtoPlusRoles(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());

        if(user.getRoles()!=null){
            List<RolesDto> rolesDto = user.getRoles().stream()
                    .map(Utils::mapRoleEntityToRolesDto)
                    .toList();
            userDto.setRoles(rolesDto);
        }
        return userDto;
    }
    public static List<UserDto> mapUsersEntityToUserDtoPlusRoles(List<User> users) {
        if (users == null || users.isEmpty()) {
            return new ArrayList<>();
        }

        return users.stream()
                .map(Utils::mapUserEntityToUserDtoPlusRoles)  // Reuse the single mapper
                .collect(Collectors.toList());
    }
    public static StaffDto mapStaffEntityToStaffDto(Staff staff) {
        StaffDto staffDto = new StaffDto();
        staffDto.setEmail(staff.getEmail());
        staffDto.setFirstName(staff.getFirstName());
        staffDto.setLastName(staff.getLastName());
        staffDto.setId(staff.getId());
        staffDto.setStaffId(staff.getStaffId());
        staffDto.setPhoneNumber(staffDto.getPhoneNumber());
        staffDto.setPosition(staffDto.getPosition());

        return staffDto;
    }
    public  static StaffDto mapStaffEntityToStaffDtoPlusDepartment(Staff staff) {
        StaffDto staffDto = Utils.mapStaffEntityToStaffDto(staff);
        if(staff.getDepartment() != null ){
            DepartmentDto departmentDto = mapDepartmentEntityToDepartmentDto(staff.getDepartment());
            staffDto.setDepartment(departmentDto);
        }
        return staffDto;
    }

}
