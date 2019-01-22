import { StudentData } from '../types';

export const STUDENT_DATA_EMPTY: StudentData = new StudentData();

export const STUDENT_DATA_FAKE: StudentData =  {
    'id': 'FAKE_KEY',
    'studentId': 'FAKE_ID',
    'lastName': 'FAKE_NAME',
    'photo': {}
};

export const STUDENT_DATA_BILLY: StudentData =  {
                                                    'id': '8a80810d5dd89a3f015dd89ac8280000',
                                                    'studentId': '1234',
                                                    'lastName': 'Billy Bob',
                                                    'photo': {
                                                        'bucketName': 'resterapp-dev',
                                                        'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
                                                    }
                                                };

export const STUDENT_DATA_BILLY_UPDATE: StudentData =  {
                                                    'id': '8a80810d5dd89a3f015dd89ac8280000',
                                                    'studentId': '007',
                                                    'lastName': 'James Bond',
                                                    'photo': {}
                                                };

export const LIST_STUDENT_DATA_EMPTY: StudentData[] = [];

export const LIST_STUDENT_DATA_TWO: StudentData[] =  [
    {
        'id': '8a80810d5dd89a3f015dd89ac8280000',
        'studentId': '1234',
        'lastName': 'Billy Bob',
        'photo': {
            'bucketName': 'resterapp-dev',
            'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
        }
    },
    {
        'id': '8a80810d5dd89a3f015dd8a252800001',
        'studentId': '503074',
        'lastName': 'Albert Russell',
        'photo': {
            'bucketName': 'resterapp-dev',
            'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
        }
    },
];

export const LIST_STUDENT_DATA_BIG: StudentData[] =  [
    {
        'id': '8a80810d5dd89a3f015dd89ac8280000',
        'studentId': '1234',
        'lastName': 'Billy Bob',
        'photo': {
            'bucketName': 'resterapp-dev',
            'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
        }
    },
    {
        'id': '8a80810d5dd89a3f015dd8a252800001',
        'studentId': '503074',
        'lastName': 'Albert Russell',
        'photo': {
            'bucketName': 'resterapp-dev',
            'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
        }
    },
    {
        'id': '8a80810d5dd89a3f015dd8a259b2000e',
        'studentId': '942226',
        'lastName': 'Alfredo Cummings',
        'photo': {}
    },
    {
        'id': '8a80810d5dd89a3f015dd8a2677f0027',
        'studentId': '824388',
        'lastName': 'Allen Russell',
        'photo': {}
    },
    {
        'id': '8a80810d5dd89a3f015dd8a271fb003a',
        'studentId': '266192',
        'lastName': 'Andre McKenna',
        'photo': {}
    },
    {
        'id': '8a80810d5dd89a3f015dd8a27f0d0052',
        'studentId': '932083',
        'lastName': 'Angelica Obrien',
        'photo': {}
    },
    {
        'id': '8a80810d5dd89a3f015dd8a281c80057',
        'studentId': '682307',
        'lastName': 'Anna Washington',
        'photo': {}
    },
    {
        'id': '8a80810d5dd89a3f015dd8a27c52004d',
        'studentId': '734552',
        'lastName': 'Antonio Fowler',
        'photo': {}
    },
    {
        'id': '8a80810d5dd89a3f015dd8a25fb80019',
        'studentId': '615643',
        'lastName': 'Ashley Mcdaniel',
        'photo': {}
    },
    {
        'id': '8a80810d5dd89a3f015dd8a25897000c',
        'studentId': '753238',
        'lastName': 'Beatrice Russell',
        'photo': {}
    },
];
