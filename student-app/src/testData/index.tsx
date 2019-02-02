import { StudentData } from '../types';

export const STUDENT_DATA_EMPTY: StudentData = new StudentData();

export const STUDENT_DATA_FAKE: StudentData =  {
    'id': 'FAKE_KEY',
    'studentId': 'FAKE_ID',
    'firstName': 'FAKE_FIRST_NAME',
    'lastName': 'FAKE_LAST_NAME',
    'photoUrl': 'FAKE_PHOTO'
};

export const STUDENT_DATA_BILLY: StudentData =  {
    'id': '8a80810d5dd89a3f015dd89ac8280000',
    'studentId': '1234',
    'firstName': 'Billy',
    'lastName': 'Bob',
    'photoUrl': 'https://static.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg'
};

export const STUDENT_DATA_BILLY_UPDATE: StudentData =  {
                                                    'id': '8a80810d5dd89a3f015dd89ac8280000',
                                                    'studentId': '007',
                                                    'firstName': 'James',
                                                    'lastName': 'Bond',
                                                    'photoUrl': ''
                                                };

export const LIST_STUDENT_DATA_EMPTY: StudentData[] = [];

export const LIST_STUDENT_DATA_TWO: StudentData[] =  [
    {
        'id': '8a80810d5dd89a3f015dd89ac8280000',
        'studentId': '1234',
        'firstName': 'Billy',
        'lastName': 'Bob',
        'photoUrl': 'https://static.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg'
    },
    {
        'id': '8a80810d5dd89a3f015dd8a252800001',
        'studentId': '503074',
        'firstName': 'Albert',
        'lastName': 'Russel',
        'photoUrl': 'https://static.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg'
    },
];

export const LIST_STUDENT_DATA_BIG: StudentData[] =  [
    {
        'id': '8a80810d5dd89a3f015dd89ac8280000',
        'studentId': '1234',
        'firstName': 'Billy',
        'lastName': 'Bob',
        'photoUrl': 'https://static.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg'
    },
    {
        'id': '8a80810d5dd89a3f015dd8a252800001',
        'studentId': '503074',
        'firstName': 'Albert',
        'lastName': 'Russell',
        'photoUrl': 'https://static.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg'
    },
    {
        'id': '8a80810d5dd89a3f015dd8a259b2000e',
        'studentId': '942226',
        'firstName': 'Alfredo',
        'lastName': 'Cummings',
        'photoUrl': ''
    },
    {
        'id': '8a80810d5dd89a3f015dd8a2677f0027',
        'studentId': '824388',
        'firstName': 'Allen',
        'lastName': 'Russell',
        'photoUrl': ''
    },
    {
        'id': '8a80810d5dd89a3f015dd8a271fb003a',
        'studentId': '266192',
        'firstName': 'Andre',
        'lastName': 'McKenna',
        'photoUrl': ''
    },
    {
        'id': '8a80810d5dd89a3f015dd8a27f0d0052',
        'studentId': '932083',
        'firstName': 'Angelica',
        'lastName': 'Obrien',
        'photoUrl': ''
    },
    {
        'id': '8a80810d5dd89a3f015dd8a281c80057',
        'studentId': '682307',
        'firstName': 'Anna',
        'lastName': 'Washington',
        'photoUrl': ''
    },
    {
        'id': '8a80810d5dd89a3f015dd8a27c52004d',
        'studentId': '734552',
        'firstName': 'Antonio',
        'lastName': 'Fowler',
        'photoUrl': ''
    },
    {
        'id': '8a80810d5dd89a3f015dd8a25fb80019',
        'studentId': '615643',
        'firstName': 'Ashley',
        'lastName': 'Mcdaniel',
        'photoUrl': ''
    },
    {
        'id': '8a80810d5dd89a3f015dd8a25897000c',
        'studentId': '753238',
        'firstName': 'Beatrice',
        'lastName': 'Russell',
        'photoUrl': ''
    },
];
