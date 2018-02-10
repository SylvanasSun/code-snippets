[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=102)](https://github.com/ellerbrock/open-source-badge/)
[![MIT license](https://img.shields.io/badge/License-MIT-blue.svg)](https://lbesson.mit-license.org/)

This repository store my code snippets and it contains the algorithms, utility components, and some toy code.

# Catalogue

```
- algorithms
    - hash
    - machine learning
    - sort
    - toy
- components
    - java
        ...
    - python
        ...
- data_structure
    - tree
    ...
```

# Algorithms

The package includes some implementation of the algorithms, maybe the categories involved are messy.

# Data_structure

The package includes some implementation of the data structure includes such as a tree, linked list, hash map, skip list and priority queue and so on.

# Components

The package includes some implementation of the reusable code components such as some kit and function module.

## Java

The component's implementation of java code.

### Package Scanner

[Link](components/java/package_scanner)

Scanning class file by specified package name then invoke callback function for handle your demand such as get all class file that specified annotations under a package.

## Python

The component's implementation of python code.

### Http utils

[Link](components/python/http_utils.py)

Packaging and simplify HTTP request by [requests library](https://github.com/requests/requests).

### Mail utils

[Link](components/python/mail_utils.py)

Simplify send email operation in python.

```python
Usage:
a = Attachment('E:\\test.jpg', 'test.jpg', 'image', 'jpg')

helper = SmtpHelper() \
            .with_server('smtp.xx.com', 25) \
            .with_third_party_service() \
            .with_ssl() \
            .with_server_login('test@xxx.com', 'xxxxxxxxxxx') \
            .with_sender('SylvanasSun', 'test@xxx.com') \
            .with_receiver('Claude Shannon', ['123456@xxx.com']) \
            .with_subject('Hello') \
            .with_attachment(a)
helper.send('Hello, World')
```
