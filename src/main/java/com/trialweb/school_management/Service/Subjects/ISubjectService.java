package com.trialweb.school_management.Service.Subjects;

import com.trialweb.school_management.Dtos.SubjectDto;
import com.trialweb.school_management.Responses.SubjectResponse;

public interface ISubjectService {
    SubjectResponse getAllSubjects();
    SubjectResponse getSubjectById(int id);
    SubjectResponse addSubject(SubjectDto subjectDto);
    SubjectResponse updateSubject(SubjectDto subjectDto);
    SubjectResponse deleteSubject(int id);
    SubjectResponse getSubjectByCode(String subjectCode);
    SubjectResponse getSubjectWithAllRelationships(int id);
}
