import * as React from 'react';
import { FormGroup, ControlLabel, FormControl, FormControlProps, Thumbnail } from 'react-bootstrap';
import { StudentData, StudentPhoto } from '../../types';

export const BASE_PHOTO_URL: string = 'https://s3-us-west-2.amazonaws.com/';
export const DEFAULT_PHOTO_URL: string = 'https://static.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg';

export interface EditStudentFormBaseProps {
    student: StudentData;
    onFormTextChange: (label: string, filter: String) => void;
    onFormFileChange: (file: File) => void;
    onStudentTextValidate: (label: String) => 'success' | 'warning' | 'error';
}

export interface EditStudentFormBaseState {
    previewURL: string;
}

export class EditStudentFormBase extends React.Component<EditStudentFormBaseProps, EditStudentFormBaseState> {

    constructor(props: EditStudentFormBaseProps) {
        super(props);

        this.state = { previewURL : '' };

        this.handleTextFieldChange = this.handleTextFieldChange.bind(this);
        this.handleFileChange = this.handleFileChange.bind(this);
        this.getPhotoURL = this.getPhotoURL.bind(this);
    }

    public handleFileChange(selectorFiles: FileList | null): void {
        if ( (selectorFiles != null ) && (selectorFiles.length > 0) ) {
            let file: File = selectorFiles[0];

            if (URL.createObjectURL != null) {
                /* A browser DOM depdendency that may not be present */
                this.setState({previewURL: URL.createObjectURL(file)});
            } else {
                this.setState({previewURL: file.name});
            }

            this.props.onFormFileChange(file);
        }
    }

    public handleTextFieldChange(e: React.FormEvent<FormControlProps>): void {
        const label = e.currentTarget.id as string;
        const value = e.currentTarget.value as string;
        console.log('handleTextFieldChange: ' + label + ' ' + value);
        this.props.onFormTextChange(label, value);
    }

    public render() {
        /* Make sure we set a default value for our form controls 
           Otherwise, React throws a warning that we are switching between uncontrolled and controlled components */
        let student = this.props.student;
        if (!student.lastName) { student.lastName = ''; }
        if (!student.firstName) { student.firstName = ''; }
        if (!student.studentId) { student.studentId = ''; }

        return (
            <form>
                <FormGroup
                    validationState={this.props.onStudentTextValidate('lastName')}
                >
                    <ControlLabel>Last Name:</ControlLabel>
                    <FormControl
                        id="lastName"
                        type="text"
                        placeholder="Enter Last Name"
                        value={student.lastName}
                        onChange={this.handleTextFieldChange}
                    />
                    <FormControl.Feedback />
                </FormGroup>
                <FormGroup
                    validationState={this.props.onStudentTextValidate('firstName')}
                >
                    <ControlLabel>First Name:</ControlLabel>
                    <FormControl
                        id="firstName"
                        type="text"
                        placeholder="Enter First Name"
                        value={student.firstName}
                        onChange={this.handleTextFieldChange}
                    />
                    <FormControl.Feedback />
                </FormGroup>                
                <FormGroup
                    validationState={this.props.onStudentTextValidate('studentId')}
                >
                    <ControlLabel>Student ID:</ControlLabel>
                    <FormControl
                        id="studentId"
                        type="text"
                        placeholder="Enter Student Id"
                        value={student.studentId}
                        onChange={this.handleTextFieldChange}
                    />
                    <FormControl.Feedback />
                </FormGroup>
                <FormGroup>
                    <ControlLabel>Photo:</ControlLabel>
                    <input
                        className="preview-photo"
                        type="file"
                        onChange={(e) => this.handleFileChange(e.target.files)}
                    />
                    <Thumbnail 
                        href="#" 
                        alt="171x180" 
                        src={this.getPhotoURL(student.photo as StudentPhoto)}
                    />
                </FormGroup>
            </form>
        );
    }

    private getPhotoURL(photo: StudentPhoto): string {
        let previewURL: string = this.state.previewURL;
        if (previewURL) {
            return previewURL;
        }
        if (photo) {
            return BASE_PHOTO_URL + photo.bucketName + '/' + photo.key;
        }   
        return DEFAULT_PHOTO_URL;
    }
}